/**
 * @file: LocalController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 08:53:18 PM
 * @description: Controlador REST que gestiona las operaciones CRUD para 'Local'.
 *  <p>Se mapea a la ruta '/api/locales' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 *  Las operaciones incluyen obtener todos los locales, obtener unn local por su ID,
 *  guardar un nuevo local, actualizar un local existente y eliminar un local.</p>
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.dto.LocalDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.LocalService;
import edu.unc.eventos.util.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/locales", headers = "Api-Version=1")
public class LocalController {
    @Autowired
    private LocalService localService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los locales existentes
     *
     * @return Lista de locales
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Local> locales = localService.getAll();
        if(locales == null || locales.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            List<LocalDTO> localesDTOs = locales.stream()
                    .map(local -> modelMapper.map(local, LocalDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<LocalDTO>> response = new ApiResponse<>(true,"Lista de locales",localesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un local por su identificador
     *
     * @param id Identificador del local
     * @return local
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Local local = localService.getById(id);
        LocalDTO localDTO = modelMapper.map(local,LocalDTO.class);
        ApiResponse<LocalDTO> response = new ApiResponse<>(true,"Local",localDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo recurso de Local en el sistema.
     *
     * Este método procesa una solicitud POST para guardar un nuevo local en el sistema.
     *
     * @param localDTO El objeto LocalDTO que contiene los datos del local a guardar.
     * @return ResponseEntity que contiene un objeto ApiResponse con información sobre el resultado de la operación.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de guardado del local.
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody LocalDTO localDTO) throws IllegalOperationException {
        Local local = modelMapper.map(localDTO, Local.class);
        localService.save(local);
        LocalDTO saveDTO = modelMapper.map(local, LocalDTO.class);
        ApiResponse<LocalDTO> response = new ApiResponse<>(true, "Local guardado", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un recurso de Local en el sistema.
     *
     * Este método procesa una solicitud PUT para actualizar un local existente en el sistema.
     *
     * @param id El identificador único del local a actualizar.
     * @param localDTO El objeto LocalDTO que contiene los datos actualizados del local.
     * @return ResponseEntity que contiene un objeto ApiResponse con información sobre el resultado de la operación.
     * @throws EntityNotFoundException Si no se encuentra el local con el identificador proporcionado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de actualización del local.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocalDTO>> update(@PathVariable Long id, @RequestBody LocalDTO localDTO) throws EntityNotFoundException,IllegalOperationException {
        Local local = modelMapper.map(localDTO, Local.class);
        localService.update(id, local);
        LocalDTO updateDTO = modelMapper.map(local, LocalDTO.class);
        ApiResponse<LocalDTO> response = new ApiResponse<>(true, "Local actualizado", updateDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un local existente por su Id
     *
     * @param id Identificador del local
     * @return Respuesta indicando el éxito de la operación
     * @throws EntityNotFoundException   Si el local no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        localService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Local eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
