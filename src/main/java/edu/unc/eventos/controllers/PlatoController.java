/**
 * @file: PlatoController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 09:11:11 PM
 * @description: Controlador REST que gestiona las operaciones CRUD para 'Plato'.
 * <p>Se mapea a la ruta '/api/platos' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todos los platos, obtener unn plato por su ID,
 * guardar un nuevo plato, actualizar un plato existente y eliminar un plato.</p>
 */

package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.dto.PlatoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.PlatoService;
import edu.unc.eventos.util.ApiResponse;
import edu.unc.eventos.util.EntityValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/platos", headers = "Api-Version=1")
public class PlatoController {
    @Autowired
    private PlatoService platoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    /**
     * Obtiene todos los Platos existentes
     *
     * @return Lista de Platos
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Plato> platos = platoService.getAll();
        if (platos == null || platos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<PlatoDTO> PlatosDTOs = platos.stream()
                    .map(Plato -> modelMapper.map(Plato, PlatoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<PlatoDTO>> response = new ApiResponse<>(true, "Lista de Platos", PlatosDTOs);
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
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Plato plato = platoService.getById(id);
        PlatoDTO PlatoDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato", PlatoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Método POST para crear un nuevo plato.
     *
     * Este método recibe un objeto PlatoDTO en el cuerpo de la solicitud y crea un nuevo plato en el sistema.
     * Realiza la validación del objeto PlatoDTO utilizando el validador de bean.
     * Si se encuentran violaciones de restricciones de validación, devuelve una respuesta con los errores de validación.
     * Si la validación es exitosa, mapea el PlatoDTO a un objeto Plato y lo guarda en la base de datos a través del servicio de plato.
     * Finalmente, devuelve una respuesta de éxito con el plato recién creado mapeado a un objeto PlatoDTO en el cuerpo de la respuesta.
     *
     * @param platoDTO El objeto PlatoDTO que contiene los datos del plato a ser creado.
     * @return ResponseEntity que contiene la respuesta de la operación, incluyendo el resultado de la operación y los detalles del plato creado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la creación del plato.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PlatoDTO platoDTO) throws IllegalOperationException {
        Plato plato = modelMapper.map(platoDTO, Plato.class);
        Set<ConstraintViolation<Plato>> violations = validator.validate (plato);
        if(!violations.isEmpty()){
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        platoService.save(plato);
        PlatoDTO saveDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato guardado", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Método PUT para actualizar un plato existente.
     *
     * Este método recibe el ID del plato a ser actualizado y un objeto PlatoDTO en el cuerpo de la solicitud.
     * Actualiza el plato correspondiente en la base de datos utilizando el servicio de plato.
     * Realiza la validación del objeto PlatoDTO utilizando el validador de bean.
     * Si se encuentran violaciones de restricciones de validación, devuelve una respuesta con los errores de validación.
     * Si la validación es exitosa y la actualización se realiza correctamente, devuelve una respuesta de éxito con el plato actualizado mapeado a un objeto PlatoDTO en el cuerpo de la respuesta.
     *
     * @param id El ID del plato a ser actualizado.
     * @param platoDTO El objeto PlatoDTO que contiene los datos actualizados del plato.
     * @return ResponseEntity que contiene la respuesta de la operación, incluyendo el resultado de la operación y los detalles del plato actualizado.
     * @throws EntityNotFoundException Si el plato con el ID especificado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la actualización del plato.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PlatoDTO platoDTO) throws EntityNotFoundException, IllegalOperationException {
        Plato plato = modelMapper.map(platoDTO, Plato.class);
        Set<ConstraintViolation<Plato>> violations = validator.validate (plato);
        if(!violations.isEmpty()){
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        platoService.update(id, plato);
        PlatoDTO updateDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato actualizado", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
