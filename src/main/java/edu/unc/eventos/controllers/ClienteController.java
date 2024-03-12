package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.domain.Evento;
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
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

        if (clientes == null || clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<ClienteDTO> clienteDTOs = clientes.stream()
                    .map(cliente -> {
                        ClienteDTO clienteDTO = modelMapper.map(cliente, ClienteDTO.class);
                        clienteDTO.add(WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(clienteDTO.getIdCliente())).withSelfRel());
                        Link eventosLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getAllEventosByIdCliente(clienteDTO.getIdCliente())).withRel("cliente-eventos");
                        clienteDTO.add(eventosLink);
                        return clienteDTO;
                    })
                    .collect(Collectors.toList());

            ApiResponse<List<ClienteDTO>> response = new ApiResponse<>(true, "Lista de clientes", clienteDTOs);
            return ResponseEntity.ok(response);
        }
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

        // Obtener el ID del cliente siguiente
        Long nextId = clienteService.getNextClienteId(id);
        if (nextId != null) {
            // Construir el enlace "next"
            Link nextLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(nextId)).withRel("next");
            clienteDTO.add(nextLink);
        }

// Obtener el ID del cliente anterior
        Long previousId = clienteService.getPreviousClienteId(id);
        if (previousId != null) {
            // Construir el enlace "previous"
            Link previousLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(previousId)).withRel("previous");
            clienteDTO.add(previousLink);
        }

// Obtener el ID del primer cliente
        Long firstId = clienteService.getFirstClienteId();
        if (firstId != null && !firstId.equals(id)) {
            // Construir el enlace "first"
            Link firstLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(firstId)).withRel("first");
            clienteDTO.add(firstLink);
        }

// Obtener el ID del último cliente
        Long lastId = clienteService.getLastClienteId();
        if (lastId != null && !lastId.equals(id)) {
            // Construir el enlace "last"
            Link lastLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getById(lastId)).withRel("last");
            clienteDTO.add(lastLink);
        }

        // Enlace a eventos relacionados con el cliente
        Link eventosLink = WebMvcLinkBuilder.linkTo(methodOn(ClienteController.class).getAllEventosByIdCliente(id)).withRel("cliente-eventos");
        clienteDTO.add(eventosLink);

        ApiResponse<ClienteDTO> response = new ApiResponse<>(true, "Cliente encontrado", clienteDTO);
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

    /**
     * Obtiene todos los eventos asociados a un cliente por su identificador.
     *
     * @param idCliente Identificador del cliente.
     * @return Lista de eventos asociados al cliente.
     * @throws EntityNotFoundException Si el cliente no se encuentra en la base de datos.
     */
    @GetMapping("/{idCliente}/eventos")
    public ResponseEntity<?> getAllEventosByIdCliente(@PathVariable Long idCliente) throws EntityNotFoundException {
        List<Evento> eventos = clienteService.getAllEventosByIdCliente(idCliente);
        if (eventos == null || eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EventoDTO> eventoDTOs = eventos.stream()
                    .map(evento -> modelMapper.map(evento, EventoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<EventoDTO>> response = new ApiResponse<>(true, "Eventos asociados al cliente", eventoDTOs);
            return ResponseEntity.ok(response);
        }
    }
}
