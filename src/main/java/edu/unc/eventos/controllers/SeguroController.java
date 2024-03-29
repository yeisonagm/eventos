/**
 * @file: SeguroController.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 02:35:18 AM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Seguro;
import edu.unc.eventos.dto.SeguroDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.SeguroService;
import edu.unc.eventos.util.ApiResponse;
import edu.unc.eventos.util.EntityValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/seguros", headers = "Api-Version=1")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los seguros.
     *
     * @return Lista de seguros.
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Seguro> seguros = seguroService.getAll();
        List<SeguroDTO> seguroDTOs = seguros.stream()
                .map(seguro -> modelMapper.map(seguro, SeguroDTO.class))
                .collect(Collectors.toList());
        ApiResponse<List<SeguroDTO>> response = new ApiResponse<>(true, "Lista de seguros", seguroDTOs);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un seguro por su identificador.
     *
     * @param id Identificador del seguro.
     * @return Seguro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Seguro seguro = seguroService.getById(id);
        SeguroDTO seguroDTO = modelMapper.map(seguro, SeguroDTO.class);
        ApiResponse<SeguroDTO> response = new ApiResponse<>(true, "Seguro", seguroDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo seguro.
     *
     * @param seguroDTO Datos del nuevo seguro.
     * @return Respuesta indicando la operación con éxito.
     * @throws IllegalOperationException Si hay una operación ilegal.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid SeguroDTO seguroDTO, BindingResult result) throws IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Seguro seguro = modelMapper.map(seguroDTO, Seguro.class);
        seguro = seguroService.save(seguro);
        SeguroDTO createdDTO = modelMapper.map(seguro, SeguroDTO.class);
        ApiResponse<SeguroDTO> response = new ApiResponse<>(true, "Seguro creado con éxito", createdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un seguro existente.
     *
     * @param id        Identificador del seguro que se quiere actualizar.
     * @param seguroDTO Datos actualizados del seguro.
     * @return Respuesta indicando la operación con éxito.
     * @throws EntityNotFoundException   Si el seguro no se encuentra en la base de datos.
     * @throws IllegalOperationException Si hay una operación ilegal.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid SeguroDTO seguroDTO, BindingResult result)
            throws EntityNotFoundException, IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Seguro seguro = modelMapper.map(seguroDTO, Seguro.class);
        seguroService.update(id, seguro);
        SeguroDTO updateDTO = modelMapper.map(seguro, SeguroDTO.class);
        ApiResponse<SeguroDTO> response = new ApiResponse<>(true, "Seguro actualizado con éxito", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina un seguro existente.
     *
     * @param id Identificador del seguro que se quiere eliminar.
     * @return Respuesta indicando la operación con éxito.
     * @throws EntityNotFoundException   Si el seguro no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el seguro está asociado a un empleado y no se puede eliminar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
            throws EntityNotFoundException, IllegalOperationException {
        seguroService.delete(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Seguro eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Asigna un seguro a un empleado.
     *
     * @param idEmpleado Id del empleado al que se le asignará el seguro.
     * @param idSeguro   Id del seguro que se asignará al empleado.
     * @return Respuesta indicando la operación con éxito.
     * @throws EntityNotFoundException   Si el empleado o el seguro no se encuentran en la base de datos.
     * @throws IllegalOperationException Si el seguro ya está asignado al empleado.
     */
    @PutMapping("/{idSeguro}/addEmpleado/{idEmpleado}")
    public ResponseEntity<?> addEmpleado(@PathVariable Long idEmpleado, @PathVariable Long idSeguro) throws EntityNotFoundException, IllegalOperationException {
        Seguro seguro = seguroService.addEmpleado(idSeguro, idEmpleado);
        SeguroDTO seguroDTO = modelMapper.map(seguro, SeguroDTO.class);
        ApiResponse<SeguroDTO> response = new ApiResponse<>(true, "Empleado asignado a su seguro correctamente", seguroDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
