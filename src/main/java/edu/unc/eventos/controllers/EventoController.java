/**
 * @file: EventoController.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 01:18:14 AM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Local;
import edu.unc.eventos.dto.EventoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.EventoService;
import edu.unc.eventos.util.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/eventos", headers = "Api-Version=1")
class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    /**
     * Obtiene todos los eventos existentes
     *
     * @return Lista de eventos
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Evento> eventos = eventoService.getAll();
        if (eventos == null || eventos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            List<EventoDTO> eventosDTOs = eventos.stream()
                    .map(evento -> modelMapper.map(evento, EventoDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<EventoDTO>> response = new ApiResponse<>(true, "Lista de eventos", eventosDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un evento por su identificador
     *
     * @param id Identificador del evento
     * @return Evento
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Evento evento = eventoService.getEventoById(id);
        EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento", eventoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crear un nuevo Evento
     *
     * @param eventoDTO Datos del nuevo Evento
     * @return Respuesta indicando la operación con éxito
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EventoDTO>> create(@RequestBody EventoDTO eventoDTO) throws IllegalOperationException {
        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        eventoService.save(evento);
        EventoDTO createdDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento creado con éxito", createdDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualizar un Evento por su ID
     *
     * @param id        Idetificador del evento
     * @param eventoDTO Nuevos datos del Evento
     * @return Respuesta indicando la operación con éxito
     * @throws EntityNotFoundException   Si el Evento no existe
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventoDTO>> update(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) throws EntityNotFoundException, IllegalOperationException {
        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        eventoService.update(id, evento);
        EventoDTO updateDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento actualizado", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Agrega un plato a un evento existente mediante una solicitud PATCH.
     *
     * Este método PATCH permite asociar un plato existente a un evento existente mediante sus identificadores.
     * Recibe el ID del evento y el ID del plato que se desea asociar como parámetros de la URL y la solicitud.
     * Llama al servicio de eventos para realizar la operación de asociación.
     * Si la operación se realiza con éxito, devuelve una respuesta con estado OK y un mensaje indicando que el plato ha sido agregado al evento correctamente.
     *
     * @param idEvento El ID del evento al que se desea añadir el plato.
     * @param idPlato El ID del plato que se desea asociar al evento.
     * @return ResponseEntity que contiene una respuesta con estado OK y un mensaje indicando que el plato ha sido agregado al evento correctamente.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la asociación del plato al evento.
     */
    @PatchMapping("/{idEvento}/addPlato")
    public ResponseEntity<String> addplato (@PathVariable Long idEvento, @RequestParam Long idPlato) throws IllegalOperationException {
        eventoService.addPlato(idEvento, idPlato);
        return ResponseEntity.ok("Plato agregado al evento correctamente");
    }

    /**
     * Eliminar un Evento por su ID
     *
     * @param id Idetificador del evento
     * @return Respuesta indicando la operación con éxito
     * @throws EntityNotFoundException   Si el Evento no existe
     * @throws IllegalOperationException Si hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        eventoService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Evento eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
