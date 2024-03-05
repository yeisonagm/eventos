package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Cliente;

import edu.unc.eventos.dto.ClienteDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.ClienteService;
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
@RequestMapping(value = "/api/clientes", headers = "Api-Version=1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    /**
     * Obtiene todos los clientes.
     *
     * @return Lista de clientes.
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Cliente> clientes = clienteService.getAll();
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
        ApiResponse<List<ClienteDTO>> response = new ApiResponse<>(true, "Lista de clientes", clienteDTOs);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un cliente por su identificador.
     *
     * @param id Identificador del cliente.
     * @return Cliente.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Cliente cliente = clienteService.getById(id);
        ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
        ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente", clienteDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo cliente.
     *
     * @param clienteDTO Datos del nuevo cliente.
     * @return Respuesta indicando la operación con éxito.
     * @throws IllegalOperationException Sí hay una operación ilegal.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClienteDTO clienteDTO) throws IllegalOperationException {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if(!violations.isEmpty()){
            EntityValidator entityValidator=new EntityValidator();
            return entityValidator.validate(violations);
        }
        cliente = clienteService.save(cliente);
        ClienteDTO createdDTO = modelMapper.map(cliente, ClienteDTO.class);
        ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente creado con éxito", createdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    /**
     * Actualiza un cliente existente.
     *
     * @param id         Identificador del cliente que se quiere actualizar.
     * @param clienteDTO Datos actualizados del cliente.
     * @return Respuesta indicando la operación con éxito.
     * @throws EntityNotFoundException   Si el cliente no se encuentra en la base de datos.
     * @throws IllegalOperationException Si hay una operación ilegal.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO)
            throws EntityNotFoundException, IllegalOperationException {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        if(!violations.isEmpty()){
            EntityValidator entityValidator=new EntityValidator();
            return entityValidator.validate(violations);
        }
        clienteService.update(id, cliente);
        ClienteDTO updateDTO = modelMapper.map(cliente, ClienteDTO.class);
        ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente actualizado con éxito", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina un cliente existente.
     *
     * @param id Identificador del cliente que se quiere eliminar.
     * @return Respuesta indicando la operación con éxito.
     * @throws EntityNotFoundException   Si el cliente no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el cliente tiene eventos asociados.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
            throws EntityNotFoundException, IllegalOperationException {
        clienteService.delete(id);
        ApiResponse<String> response = new ApiResponse<>(true, "Cliente eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
