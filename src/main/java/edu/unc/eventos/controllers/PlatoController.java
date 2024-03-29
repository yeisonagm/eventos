/**
 * @file: PlatoController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 09:11:11 PM
 * @description: Controlador REST que gestiona las operaciones CRUD para 'Plato'.
 * <p>Se mapea a la ruta '/api/platos' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todos los platos, obtener unn plato por su ID,
 * guardar un nuevo plato, actualizar un plato existente y eliminar un plato.</p>
 */

package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.dto.EventoDTO;
import edu.unc.eventos.dto.PlatoDTO;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.services.PlatoService;
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
@RequestMapping(value = "/api/platos", headers = "Api-Version=1")
public class PlatoController {
    @Autowired
    private PlatoService platoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los platos disponibles.
     * <p>
     * Este método GET permite recuperar todos los platos disponibles en el sistema.
     * Retorna una lista de todos los platos en forma de ResponseEntity.
     * Si no se encuentran platos disponibles, devuelve una respuesta sin contenido (status 204).
     * Si se encuentran platos disponibles, los convierte en DTOs (Data Transfer Objects), los agrega a una ApiResponse y retorna una respuesta con estado OK (status 200) junto con la lista de platos en formato DTO.
     *
     * @return ResponseEntity que contiene una lista de platos en formato DTO o una respuesta sin contenido si no hay platos disponibles.
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Plato> platos = platoService.getAll();
        if (platos == null || platos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<PlatoDTO> PlatosDTOs = platos.stream()
                    .map(Plato -> modelMapper.map(Plato, PlatoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<PlatoDTO>> response = new ApiResponse<>(true, "Lista de Platos", PlatosDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un plato por su ID.
     * <p>
     * Este método GET permite recuperar un plato específico mediante su ID.
     * Recibe el ID del plato como parámetro en la URL y retorna una respuesta con el plato correspondiente.
     * Si se encuentra el plato, lo convierte en un DTO (Data Transfer Object), lo agrega a una ApiResponse y retorna una respuesta con estado OK (status 200) junto con el plato en formato DTO.
     *
     * @param id El ID del plato que se desea recuperar.
     * @return ResponseEntity que contiene el plato en formato DTO.
     * @throws EntityNotFoundException Si el plato con el ID especificado no se encuentra en la base de datos.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Plato plato = platoService.getById(id);
        PlatoDTO PlatoDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato", PlatoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una lista de eventos asociados a un plato mediante su ID.
     * <p>
     * Este método GET permite recuperar todos los eventos asociados a un plato específico.
     * Recibe el ID del plato como parámetro en la URL y retorna una lista de eventos asociados a ese plato.
     *
     * @param platoId El ID del plato del cual se desean obtener los eventos.
     * @return ResponseEntity que contiene una respuesta con estado OK y una lista de eventos asociados al plato especificado.
     * @throws EntityNotFoundException Si el plato con el ID especificado no se encuentra en la base de datos.
     */
    @GetMapping("/{platoId}/eventos")
    public ResponseEntity<?> getEventosByPlatoId(@PathVariable Long platoId) {
        List<Evento> eventos = platoService.getEventosByPlatoId(platoId);
        List<EventoDTO> eventoDTOS = eventos.stream().map(evento -> modelMapper.map(evento, EventoDTO.class)).collect(Collectors.toList());
        ApiResponse<List<EventoDTO>> response = new ApiResponse<>(true, "Lista de Eventos", eventoDTOS);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un evento asociado a un plato mediante los IDs de plato y evento.
     * <p>
     * Este método GET permite recuperar un evento específico que está asociado a un plato determinado.
     * Recibe los IDs del plato y del evento como parámetros en la URL y retorna el evento asociado a ese plato.
     *
     * @param platoId  El ID del plato del cual se desea obtener el evento.
     * @param eventoId El ID del evento que se desea recuperar.
     * @return ResponseEntity que contiene una respuesta con estado OK y el evento asociado al plato especificado.
     * @throws EntityNotFoundException Si el plato con el ID especificado no se encuentra en la base de datos o si el evento con el ID especificado no se encuentra en el plato.
     */
    @GetMapping("/{platoId}/eventos/{eventoId}")
    public ResponseEntity<?> getEventoByPlatoId(@PathVariable Long platoId, @PathVariable Long eventoId) {
        Evento evento = platoService.getEventoByPlatoId(platoId, eventoId);
        EventoDTO eventoDTO = modelMapper.map(evento, EventoDTO.class);
        ApiResponse<EventoDTO> response = new ApiResponse<>(true, "Evento Encontrado", eventoDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Método POST para crear un nuevo plato.
     * <p>
     * Este método recibe un objeto PlatoDTO en el cuerpo de la solicitud y crea un nuevo plato en el sistema.
     * Realiza la validación del objeto PlatoDTO utilizando el validador de bean.
     * Si se encuentran violaciones de restricciones de validación, devuelve una respuesta con los errores de validación.
     * Si la validación es exitosa, mapea el PlatoDTO a un objeto Plato y lo guarda en la base de datos a través del servicio de plato.
     * Finalmente, devuelve una respuesta de éxito con el plato recién creado mapeado a un objeto PlatoDTO en el cuerpo de la respuesta.
     *
     * @param platoDTO El objeto PlatoDTO que contiene los datos del plato a ser creado.
     * @return ResponseEntity que contiene la respuesta de la operación, incluyendo el resultado de la operación y los detalles del plato creado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la creación del plato.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PlatoDTO platoDTO, BindingResult result) throws IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Plato plato = modelMapper.map(platoDTO, Plato.class);
        platoService.save(plato);
        PlatoDTO saveDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato guardado", saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Método PUT para actualizar un plato existente.
     * <p>
     * Este método recibe el ID del plato a ser actualizado y un objeto PlatoDTO en el cuerpo de la solicitud.
     * Actualiza el plato correspondiente en la base de datos utilizando el servicio de plato.
     * Realiza la validación del objeto PlatoDTO utilizando el validador de bean.
     * Si se encuentran violaciones de restricciones de validación, devuelve una respuesta con los errores de validación.
     * Si la validación es exitosa y la actualización se realiza correctamente, devuelve una respuesta de éxito con el plato actualizado mapeado a un objeto PlatoDTO en el cuerpo de la respuesta.
     *
     * @param id       El ID del plato a ser actualizado.
     * @param platoDTO El objeto PlatoDTO que contiene los datos actualizados del plato.
     * @return ResponseEntity que contiene la respuesta de la operación, incluyendo el resultado de la operación y los detalles del plato actualizado.
     * @throws EntityNotFoundException   Si el plato con el ID especificado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la actualización del plato.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PlatoDTO platoDTO, BindingResult result) throws EntityNotFoundException, IllegalOperationException {
        if (result.hasErrors()) return new EntityValidator().validate(result);

        Plato plato = modelMapper.map(platoDTO, Plato.class);
        platoService.update(id, plato);
        PlatoDTO updateDTO = modelMapper.map(plato, PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true, "Plato actualizado", updateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina un plato existente por su Id
     *
     * @param id Identificador del plato
     * @return Respuesta indicando el éxito de la operación
     * @throws EntityNotFoundException   Si el plato no existe
     * @throws IllegalOperationException Sí hay una operación ilegal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        platoService.delete(id);
        ApiResponse<?> response = new ApiResponse<>(true, "Plato eliminado con éxito", null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
