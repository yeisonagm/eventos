/**
 * @file: PlatoController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 09:11:11 PM
 * @description: Controlador REST que gestiona las operaciones CRUD para 'Plato'.
 *  <p>Se mapea a la ruta '/api/platos' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 *  Las operaciones incluyen obtener todos los platos, obtener unn plato por su ID,
 *  guardar un nuevo plato, actualizar un plato existente y eliminar un plato.</p>
 */

package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.dto.PlatoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.PlatoService;
import edu.unc.eventos.util.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/platos", headers = "Api-Version=1")
public class PlatoController {
    @Autowired
    private PlatoService platoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los Platos existentes
     *
     * @return Lista de Platos
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Plato> platos = platoService.getAll();
        if(platos == null || platos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            List<PlatoDTO> PlatosDTOs = platos.stream()
                    .map(Plato -> modelMapper.map(Plato, PlatoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<PlatoDTO>> response = new ApiResponse<>(true,"Lista de Platos",PlatosDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un plato por su identificador
     *
     * @param id Identificador del plato
     * @return plato
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Plato plato = platoService.getById(id);
        PlatoDTO PlatoDTO = modelMapper.map(plato,PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true,"Plato",PlatoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo recurso de Plato en el sistema.
     *
     * Este método procesa una solicitud POST para guardar un nuevo plato en el sistema.
     *
     * @param platoDTO El objeto PlatoDTO que contiene los datos del plato a guardar.
     * @return ResponseEntity que contiene un objeto ApiResponse con información sobre el resultado de la operación.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de guardado del plato.
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody PlatoDTO platoDTO) throws IllegalOperationException {
        Plato plato = modelMapper.map(platoDTO, Plato.class);
        platoService.save(plato);
        PlatoDTO saveDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato guardado", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un recurso de Plato en el sistema.
     *
     * Este método procesa una solicitud PUT para actualizar un plato existente en el sistema.
     *
     * @param id El identificador único del plato a actualizar.
     * @param platoDTO El objeto PlatoDTO que contiene los datos actualizados del plato.
     * @return ResponseEntity que contiene un objeto ApiResponse con información sobre el resultado de la operación.
     * @throws EntityNotFoundException Si no se encuentra el plato con el identificador proporcionado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de actualización del plato.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlatoDTO>> update(@PathVariable Long id, @RequestBody PlatoDTO platoDTO) throws EntityNotFoundException,IllegalOperationException {
        Plato plato = modelMapper.map(platoDTO, Plato.class);
        platoService.update(id, plato);
        PlatoDTO updateDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato actualizado", updateDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un plato existente por su Id
     *
     * @param id Identificador del plato
     * @return Respuesta indicando el éxito de la operación
     * @throws EntityNotFoundException   Si el plato no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        platoService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Plato eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
