/**
 * @file: ApplicationConfig.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 11:30:18 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.dto.DecoracionDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.DecoracionService;
import edu.unc.eventos.util.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST que gestiona las operaciones CRUD para 'Decoraciones'.
 * <p>Se mapea a la ruta '/api/decoraciones' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todas las decoraciones, obtener una decoración por su ID,
 * guardar una nueva decoración, actualizar una decoración existente y eliminar una decoración.</p>
 */
@RestController
@RequestMapping(value = "/api/decoraciones", headers = "Api-Version=1")
public class DecoracionController {
    @Autowired
    private DecoracionService decoracionService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todas las decoraciones existentes
     *
     * @return Lista de decoraciones
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Decoracion> decoraciones = decoracionService.getAll();
        if (decoraciones == null || decoraciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<DecoracionDTO> decoracionesDTOs = decoraciones.stream()
                    .map(decoracion -> modelMapper.map(decoracion, DecoracionDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<DecoracionDTO>> response = new ApiResponse<>(true, "Lista de decoraciones", decoracionesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene una decoración por su identificador
     *
     * @param id Identificador de la decoración
     * @return Decoración
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Decoracion decoracion = decoracionService.getById(id);
        DecoracionDTO decoracionDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración", decoracionDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea una nueva decoración
     *
     * @param decoracionDTO Datos nuevos de la decoración
     * @return Decoración creada
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @PostMapping
    public ResponseEntity<?> save(@RequestBody DecoracionDTO decoracionDTO) throws IllegalOperationException {
        Decoracion decoracion = modelMapper.map(decoracionDTO, Decoracion.class);
        decoracionService.save(decoracion);
        DecoracionDTO saveDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración guardada", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una decoración existente
     *
     * @param id            Identificador de la decoración
     * @param decoracionDTO Datos actualizados de la decoración
     * @return Decoración actualizada
     * @throws EntityNotFoundException   Si la decoración no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DecoracionDTO>> update(@PathVariable Long id, @RequestBody DecoracionDTO decoracionDTO) throws EntityNotFoundException, IllegalOperationException {
        Decoracion decoracion = modelMapper.map(decoracionDTO, Decoracion.class);
        decoracionService.update(id, decoracion);
        DecoracionDTO updateDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración actualizada", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina una decoración existente por su Id
     *
     * @param id Identificador de la decoración
     * @return Respuesta indicando el éxito de la operación
     * @throws EntityNotFoundException   Si la decoración no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        decoracionService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Rol eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
