/**
 * @file: RolController.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 00:04:45 AM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.dto.RolDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.RolService;
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
 * Controlador REST que gestiona las operaciones CRUD para 'Roles'.
 * <p>Se mapea a la ruta '/api/roles' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todos las roles, obtener una rol por su ID,
 * guardar un nuevo rol, actualizar un rol existente y eliminar un rol.</p>
 */
@RestController
@RequestMapping(value = "/api/roles", headers = "Api-Version=1")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    /**
     * Obtiene todos los roles existentes
     *
     * @return Lista de roles
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Rol> roles = rolService.getAll();
        if (roles == null || roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<RolDTO> rolesDTOs = roles.stream()
                    .map(rol -> modelMapper.map(rol, RolDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<RolDTO>> response = new ApiResponse<>(true, "Lista de roles", rolesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un rol por su identificador
     *
     * @param id Idetificador del rol
     * @return Rol
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Rol rol = rolService.getRolById(id);
        RolDTO rolDTO = modelMapper.map(rol, RolDTO.class);
        ApiResponse<RolDTO> response = new ApiResponse<>(true, "Rol", rolDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crear un nuevo Rol
     *
     * @param rolDTO Datos del nuevo Rol
     * @return Respuesta indicando la operación con éxito
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RolDTO rolDTO) throws IllegalOperationException {
        Rol rol = modelMapper.map(rolDTO, Rol.class);
        // Realiza la validación, si hay errores de validación, maneja los errores
        Set<ConstraintViolation<Rol>> violations = validator.validate(rol);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        rolService.save(rol);
        RolDTO createdDTO = modelMapper.map(rol, RolDTO.class);
        ApiResponse<RolDTO> response = new ApiResponse<>(true, "Rol creado con éxito", createdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualizar un Rol por su ID
     *
     * @param id     Idetificador del rol
     * @param rolDTO Nuevos datos del Rol
     * @return Respuesta indicando la operación con éxito
     * @throws EntityNotFoundException   Si el Rol no existe
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RolDTO rolDTO) throws EntityNotFoundException, IllegalOperationException {
        Rol rol = modelMapper.map(rolDTO, Rol.class);
        // Realiza la validación, si hay errores de validación, maneja los errores
        Set<ConstraintViolation<Rol>> violations = validator.validate(rol);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        rolService.update(id, rol);
        RolDTO updateDTO = modelMapper.map(rol, RolDTO.class);
        ApiResponse<RolDTO> response = new ApiResponse<>(true, "Rol actualizado", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Eliminar un Rol por su ID
     *
     * @param id Idetificador del rol
     * @return Respuesta indicando la operación con éxito
     * @throws EntityNotFoundException   Si el Rol no existe
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        rolService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Rol eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}