/**
 * @file: ApplicationConfig.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 09:30:18 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.dto.EmpleadoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.EmpleadoService;
import edu.unc.eventos.util.ApiResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador REST que gestiona las operaciones CRUD para 'Empleados'.
 * <p>Se mapea a la ruta '/api/empleados' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todos los empleados, obtener un empleado por su ID,
 * guardar un nuevo empleado, actualizar un empleado existente y eliminar un empleado.</p>
 */
@RestController
@RequestMapping(value = "/api/empleados", headers = "Api-Version=1")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todas los empleados existentes
     *
     * @return Lista de empleados
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Empleado> empleados = empleadoService.getAll();
        if (empleados == null || empleados.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EmpleadoDTO> empleadosDTOs = empleados.stream()
                    .map(empleado -> modelMapper.map(empleado, EmpleadoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<EmpleadoDTO>> response = new ApiResponse<>(true, "Lista de empleados", empleadosDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un empleado por su identificador
     *
     * @param id Identificador del empleado
     * @return El empleado que se encontró
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Empleado empleado = empleadoService.getById(id);
        EmpleadoDTO empleadoDTO = modelMapper.map(empleado, EmpleadoDTO.class);
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado encontrado", empleadoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo empleado
     *
     * @param empleadoDTO Datos del empleado que se quiere guardar
     * @return El empleado que se guardó
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody EmpleadoDTO empleadoDTO, BindingResult result) throws IllegalOperationException {
        Empleado empleado = modelMapper.map(empleadoDTO, Empleado.class);
        if (result.hasErrors()) {
            return validate(result);
        }
        empleadoService.save(empleado);
        EmpleadoDTO saveDTO = modelMapper.map(empleado, EmpleadoDTO.class);
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado guardado", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un empleado existente
     *
     * @param id          Identificador del empleado que se quiere actualizar
     * @param empleadoDTO Datos actualizados del empleado
     * @return El empleado que se actualizó
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmpleadoDTO>> update(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) throws EntityNotFoundException, IllegalOperationException {
        Empleado empleado = modelMapper.map(empleadoDTO, Empleado.class);
        empleadoService.update(id, empleado);
        EmpleadoDTO updateDTO = modelMapper.map(empleado, EmpleadoDTO.class);
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Decoración actualizada", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina un empleado existente
     *
     * @param id Identificador del empleado que se quiere eliminar
     * @return Respuesta de la operación
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        empleadoService.delete(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Empleado eliminado", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * Maneja los errores generados por la validación de los datos de las entidades
     *
     * @param result Resultado de la validación
     * @return Mapa con los errores
     */
    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
