package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Cliente;

import edu.unc.eventos.dto.ClienteDTO;
import edu.unc.eventos.dto.EventoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.ClienteService;
import edu.unc.eventos.util.ApiResponse;
import edu.unc.eventos.util.EntityValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/clientes", headers = "Api-Version=1")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los clientes.
     *
     * @return Lista de clientes.
     */

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Cliente> clientes = clienteService.getAll();
        List<ClienteDTO> clienteDTOs = clientes.stream()
                .map(cliente -> {
                    ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
                    clienteDTO.add(WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(clienteDTO.getIdCliente())).withSelfRel());
                    return clienteDTO;
                })
                .collect(Collectors.toList());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Api-Version", "1");

        return ResponseEntity.ok().headers(headers).body(clienteDTOs);
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
        // Mapear los eventos de cada cliente y agregarlos al DTO
        List<EventoDTO> eventoDTOs = cliente.getEventos().stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
        clienteDTO.setEventos(eventoDTOs);

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
    public ResponseEntity<?> create(@RequestBody @Valid ClienteDTO clienteDTO, BindingResult result) throws IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
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
     * @throws IllegalOperationException Sí hay una operación ilegal.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClienteDTO clienteDTO, BindingResult result)
            throws EntityNotFoundException, IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
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
