/**
 * @file: RolController.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 00:04:45 AM
 */
package edu.unc.eventos.controllers;
import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.dto.EmpleadoDTO;
import edu.unc.eventos.dto.RolDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.RolService;
import edu.unc.eventos.util.ApiResponse;
import edu.unc.eventos.util.EntityValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * Obtiene todos los roles existentes
     *
     * @return Lista de roles
     */
    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        List<Rol> roles = rolService.getAll();

        if (roles == null || roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<RolDTO> rolesDTOs = roles.stream()
                    .map(rol -> {
                        RolDTO rolDTO = modelMapper.map(rol, RolDTO.class);
                        rolDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RolController.class).getById(rolDTO.getIdRol())).withSelfRel());
                        Link empleadosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RolController.class).getAllEmpleadosByIdRol(rolDTO.getIdRol())).withRel("roles-empleados");
                        rolDTO.add(empleadosLink);
                        return rolDTO;
                    })
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
    public ResponseEntity<?> create(@RequestBody @Valid RolDTO rolDTO, BindingResult result) throws IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Rol rol = modelMapper.map(rolDTO, Rol.class);
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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid RolDTO rolDTO, BindingResult result)
            throws EntityNotFoundException, IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Rol rol = modelMapper.map(rolDTO, Rol.class);
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

    /**
     * Obtiene todos los empleados asociados a un Rol por su ID
     *
     * @param idRol Identificador del Rol
     * @return Lista de empleados
     */
    @GetMapping("/{idRol}/empleados")
    public ResponseEntity<?> getAllEmpleadosByIdRol(@PathVariable Long idRol) {
        List<Empleado> empleados = rolService.getAllEmpleadosByIdRol(idRol);
        if (empleados== null){
            return ResponseEntity.noContent().build();
        }else{
            List<EmpleadoDTO> empleadoDTOS = empleados.stream()
                    .map(empleado -> modelMapper.map(empleado, EmpleadoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<EmpleadoDTO>> response = new ApiResponse<>(true, "Empleaods asociados a rol", empleadoDTOS);
            return ResponseEntity.ok(response);
        }
    }
    /**
     * Obtiene un empleado por su ID y el ID de su Rol asociado
     *
     * @param idRol     Identificador del Rol
     * @param idEmpleado Identificador del Empleado
     * @return Empleado
     */
    @GetMapping("/{idRol}/empleados/{idEmpleado}")
    public ResponseEntity<?> getByIdEmpleadoByRolId(@PathVariable Long idRol, @PathVariable Long idEmpleado) {
        Empleado empleado = rolService.getByIdEmpleadoByRolId(idRol, idEmpleado);
        if(empleado==null){
            ApiResponse<String> response = new ApiResponse<>(false, "Empleado no encontrado", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }else{
            EmpleadoDTO empleadoDTO = modelMapper.map(empleado, EmpleadoDTO.class);
            ApiResponse<EmpleadoDTO> response = new ApiResponse<>(true, "Empleado encontrado", empleadoDTO);
            return ResponseEntity.ok(response);
        }
    }


}