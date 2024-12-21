/**
 * @file: EventoController.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 01:18:14 AM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.dto.EventoDTO;
import edu.unc.eventos.dto.PlatoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.EventoService;
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
@RequestMapping(value = "/api/eventos", headers = "Api-Version=1")
@CrossOrigin(origins = "http://localhost:3000")
class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los eventos existentes
     *
     * @return Lista de eventos
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Evento> eventos = eventoService.getAll();
        if (eventos == null || eventos.isEmpty()) {
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
     * Obtiene una lista de platos asociados a un evento mediante su ID.
     * <p>
     * Este método GET permite recuperar todos los platos asociados a un evento específico.
     * Recibe el ID del evento como parámetro en la URL y retorna una lista de platos asociados a ese evento.
     *
     * @param eventoId El ID del evento del cual se desean obtener los platos.
     * @return ResponseEntity que contiene una respuesta con estado OK y una lista de platos asociados al evento especificado.
     * @throws EntityNotFoundException Si el evento con el ID especificado no se encuentra en la base de datos.
     */
    @GetMapping("/{eventoId}/platos")
    public ResponseEntity<?> getPlatosByEventoId(@PathVariable Long eventoId) {
        List<Plato> platos = eventoService.getPlatosByEventoId(eventoId);
        List<PlatoDTO> platoDTOS = platos.stream().map(plato -> modelMapper.map(plato, PlatoDTO.class)).collect(Collectors.toList());
        ApiResponse<List<PlatoDTO>> response = new ApiResponse<>(true, "Lista de platos", platoDTOS);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un plato asociado a un evento mediante los IDs de evento y plato.
     * <p>
     * Este método GET permite recuperar un plato específico que está asociado a un evento determinado.
     * Recibe los IDs del evento y del plato como parámetros en la URL y retorna el plato asociado a ese evento.
     *
     * @param eventoId El ID del evento del cual se desea obtener el plato.
     * @param platoId  El ID del plato que se desea recuperar.
     * @return ResponseEntity que contiene una respuesta con estado OK y el plato asociado al evento especificado.
     * @throws EntityNotFoundException Si el evento con el ID especificado no se encuentra en la base de datos o si el plato con el ID especificado no se encuentra en el evento.
     */
    @GetMapping("/{eventoId}/platos/{platoId}")
    public ResponseEntity<?> getPlatoByEventoId(@PathVariable Long eventoId, @PathVariable Long platoId) {
        Plato plato = eventoService.getPlatoByEventoId(eventoId, platoId);
        PlatoDTO platoDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato encontrado", platoDTO);
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
    public ResponseEntity<?> create(@RequestBody @Valid EventoDTO eventoDTO, BindingResult result) throws IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

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
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid EventoDTO eventoDTO, BindingResult result)
            throws EntityNotFoundException, IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        eventoService.update(id, evento);
        EventoDTO updateDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento actualizado", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Agrega un plato a un evento existente mediante una solicitud PATCH.
     * <p>
     * Este método PATCH permite asociar un plato existente a un evento existente mediante sus identificadores.
     * Recibe el ID del evento y el ID del plato que se desea asociar como parámetros de la URL y la solicitud.
     * Llama al servicio de eventos para realizar la operación de asociación.
     * Si la operación se realiza con éxito, devuelve una respuesta con estado OK y un mensaje indicando que el plato ha sido agregado al evento correctamente.
     *
     * @param idEvento El ID del evento al que se desea añadir el plato.
     * @param idPlato  El ID del plato que se desea asociar al evento.
     * @return ResponseEntity que contiene una respuesta con estado OK y un mensaje indicando que el plato ha sido agregado al evento correctamente.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la asociación del plato al evento.
     */
    @PatchMapping("/{idEvento}/addPlato")
    public ResponseEntity<?> addPlato(@PathVariable Long idEvento, @RequestParam Long idPlato) throws IllegalOperationException {
        Evento evento = eventoService.addPlato(idEvento, idPlato);
        EventoDTO updatedDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Plato agregado al evento correctamente", updatedDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Agrega una decoracion a un evento existente mediante una solicitud PATCH.
     *
     * @param idEvento     Identificador del Evento
     * @param idDecoracion Identificador de la Decoracion
     * @return Mensaje confirmando la operacion
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la asociación de la decoracion al evento.
     */
    @PatchMapping("/{idEvento}/addDecoracionToEvento")
    public ResponseEntity<?> addDecoracionToEvento(@PathVariable Long idEvento, @RequestParam Long idDecoracion) throws IllegalOperationException {
        eventoService.addDecoracionToEvento(idEvento, idDecoracion);
        return ResponseEntity.ok("Decoracion agregada al evento correctamente");
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
