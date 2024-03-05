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

    @Autowired
    private Validator validator;

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
    @PatchMapping("/{empleadoId}/supervisor/{supervisorId}")
    public ResponseEntity<String> addSupervisor(@PathVariable Long empleadoId, @PathVariable Long supervisorId) throws IllegalOperationException {
        empleadoService.addSupervisor(empleadoId, supervisorId);
        return ResponseEntity.ok("Supervisor actualizado correctamente");
    }

    /**
     * Crea un nuevo empleado
     *
     * @param empleadoDTO Datos del nuevo empleado
     * @return El empleado que se guardó
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmpleadoDTO empleadoDTO) throws IllegalOperationException {
        Empleado empleado = modelMapper.map(empleadoDTO, Empleado.class);
        // Realiza la validación, si hay errores de validación, maneja los errores
        Set<ConstraintViolation<Empleado>> violations = validator.validate(empleado);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) throws EntityNotFoundException, IllegalOperationException {
        Empleado empleado = modelMapper.map(empleadoDTO, Empleado.class);
        // Realiza la validación, si hay errores de validación, maneja los errores
        Set<ConstraintViolation<Empleado>> violations = validator.validate(empleado);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
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
     * Asigna un rol a un empleado.
     *
     * @param idEmpleado ID del empleado al que se asignará el rol
     * @param idRol      ID del rol que se asignará al empleado
     * @return ResponseEntity con el resultado de la operación
     * @throws EntityNotFoundException   Si no se encuentra el empleado o el rol
     * @throws IllegalOperationException Si el empleado ya tiene asignado el rol proporcionado
     */
    @PatchMapping("/{idEmpleado}/addRolToEmpleado/{idRol}")
    public ResponseEntity<?> addRolToEmpleado(@PathVariable Long idEmpleado, @PathVariable Long idRol) throws EntityNotFoundException, IllegalOperationException {
        empleadoService.addRolToEmpleado(idEmpleado, idRol);
        return ResponseEntity.ok("Rol asignado al empleado correctamente");
    }

    /**
     * Asigna el supervisor de un empleado.
     *
     * @param idEmpleado   ID del empleado al que se asignará el supervisor
     * @param idSupervisor ID del supervisor que se asignará al empleado
     * @return ResponseEntity con el resultado de la operación
     * @throws EntityNotFoundException   Si no se encuentra el empleado o el supervisor
     * @throws IllegalOperationException Si el empleado ya tiene asignado el supervisor proporcionado
     */
    @PatchMapping("/{idEmpleado}/addSupervisor/{idSupervisor}")
    public ResponseEntity<?> addSupervisorToEmpleado(@PathVariable Long idEmpleado, @PathVariable Long idSupervisor) throws EntityNotFoundException, IllegalOperationException {
        Empleado empleado = empleadoService.addSupervisor(idEmpleado, idSupervisor);
        EmpleadoDTO empleadoDTO = modelMapper.map(empleado, EmpleadoDTO.class);
        ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Supervisor asignado al empleado correctamente", empleadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
