/**
 * @file: ApplicationConfig.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 11:30:18 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.dto.DecoracionDTO;
import edu.unc.eventos.dto.EventoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.DecoracionService;
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
 * Controlador REST que gestiona las operaciones CRUD para 'Decoraciones'.
 * <p>Se mapea a la ruta '/api/decoraciones' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todas las decoraciones, obtener una decoración por su ID,
 * guardar una nueva decoración, actualizar una decoración existente y eliminar una decoración.</p>
 */
@RestController
@RequestMapping(value = "/api/decoraciones", headers = "Api-Version=1")
public class DecoracionController {
    @Autowired
    private DecoracionService decoracionService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validator validator;

    /**
     * Obtiene todas las decoraciones existentes
     *
     * @return Lista de decoraciones
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Decoracion> decoraciones = decoracionService.getAll();
        if (decoraciones == null || decoraciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<DecoracionDTO> decoracionesDTOs = decoraciones.stream()
                    .map(decoracion -> modelMapper.map(decoracion, DecoracionDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<DecoracionDTO>> response = new ApiResponse<>(true, "Lista de decoraciones", decoracionesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene una decoración por su identificador
     *
     * @param id Identificador de la decoración
     * @return Decoración
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Decoracion decoracion = decoracionService.getById(id);
        DecoracionDTO decoracionDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración encontrada", decoracionDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Crea una nueva decoración
     *
     * @param decoracionDTO Datos nuevos de la decoración
     * @return Decoración creada
     * @throws IllegalOperationException Sí se genera una operación ilegal
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody DecoracionDTO decoracionDTO) throws IllegalOperationException {
        Decoracion decoracion = modelMapper.map(decoracionDTO, Decoracion.class);
        Set<ConstraintViolation<Decoracion>> violations = validator.validate(decoracion);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        decoracionService.save(decoracion);
        DecoracionDTO saveDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración guardada", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una decoración existente
     *
     * @param id            Identificador de la decoración
     * @param decoracionDTO Datos actualizados de la decoración
     * @return Decoración actualizada
     * @throws EntityNotFoundException   Si la decoración no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DecoracionDTO decoracionDTO) throws EntityNotFoundException, IllegalOperationException {
        Decoracion decoracion = modelMapper.map(decoracionDTO, Decoracion.class);
        Set<ConstraintViolation<Decoracion>> violations = validator.validate(decoracion);
        if (!violations.isEmpty()) {
            EntityValidator entityValidator = new EntityValidator();
            return entityValidator.validate(violations);
        }
        decoracionService.update(id, decoracion);
        DecoracionDTO updateDTO = modelMapper.map(decoracion, DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración actualizada", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina una decoración existente por su Id
     *
     * @param id Identificador de la decoración
     * @return Respuesta indicando el éxito de la operación
     * @throws EntityNotFoundException   Si la decoración no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        decoracionService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Decoración eliminada con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Este controlador permite recuperar todos los eventos asociados a una decoracion
     *
     * @param idDecoracion Identificador de la decoración
     * @return Retorna la lista de eventos asociados a la decoración
     * @throws EntityNotFoundException Si el Id de la decoración no existe en la DB
     */
    @GetMapping("/{idDecoracion}/eventos")
    public ResponseEntity<?> getAllEventosByIdDecoracion(@PathVariable Long idDecoracion) throws EntityNotFoundException {
        List<Evento> eventos = decoracionService.getAllEventosByIdDecoracion(idDecoracion);
        if (eventos == null || eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<EventoDTO> eventoDTOs = eventos.stream()
                    .map(evento -> modelMapper.map(evento, EventoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<EventoDTO>> response = new ApiResponse<>(true, "Eventos asociados a la decoracion", eventoDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Este controlodaro devuelve un evento específico que tiene asignado una decoración específica
     *
     * @param idDecoracion Identificador de la decoración
     * @param idEvento     Identificador del evento
     * @return Retorna un evento.
     */
    @GetMapping("/{idDecoracion}/eventos/{idEvento}")
    public ResponseEntity<?> getByIdEventoByIdEvento(@PathVariable Long idDecoracion, @PathVariable Long idEvento) {
        Evento evento = decoracionService.getByIdEventoByIdEvento(idDecoracion, idEvento);
        if (evento == null) {
            ApiResponse<String> response = new ApiResponse<>(false, "Evento no encontrado", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
            ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento encontrado", eventoDTO);
            return ResponseEntity.ok(response);

        }
    }
}
