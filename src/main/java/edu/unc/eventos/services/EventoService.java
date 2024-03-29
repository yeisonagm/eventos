/**
 * @file: EventoService.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 10:39:39 PM
 * @description: Interfaz que define los servicios disponibles para la gestión de eventos en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de eventos.
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface EventoService {
    /**
     * Recupera todos los eventos registrados.
     *
     * @return Lista de objetos Evento.
     */
    List<Evento> getAll();

    /**
     * Obtiene una lista de platos asociados a un evento específico.
     * <p>
     * Recibe el ID del evento como parámetro y retorna una lista de platos asociados a ese evento.
     *
     * @param eventoId El ID del evento del cual se desean obtener los platos.
     * @return Lista de platos asociados al evento especificado.
     */
    List<Plato> getPlatosByEventoId(Long eventoId);

    /**
     * Obtiene un plato específico asociado a un evento.
     * <p>
     * Recibe el ID del evento y el ID del plato como parámetros y retorna el plato asociado a ese evento.
     *
     * @param eventoId El ID del evento del cual se desea obtener el plato.
     * @param platoId  El ID del plato que se desea recuperar.
     * @return El plato asociado al evento especificado.
     */
    Plato getPlatoByEventoId(Long eventoId, Long platoId);

    /**
     * Recupera un evento específico según su ID
     *
     * @param idEvento Idetificador único del evento.
     * @return Objeto Evento correspondiente al ID proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún evento con el identificador especificado.
     */
    Evento getEventoById(Long idEvento) throws EntityNotFoundException;

    /**
     * Guarda un nuevo local
     *
     * @param evento El objeto Evento que se va a guardar
     * @return El objeto Evento guardado
     * @throws IllegalOperationException Si hay algún problema con los datos del evento que impide su guardado.
     */
    Evento save(Evento evento) throws IllegalOperationException;

    /**
     * Actualiza un evento axistente.
     *
     * @param idEvento El identificador único del evento que se va a actualizar
     * @param evento   El objeto Evento con los nuevos datos
     * @return El objeto Evento actualizado
     * @throws EntityNotFoundException   Si no se encuentra ningún evento con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema con los datos del evento que impide su actualización.
     */
    Evento update(Long idEvento, Evento evento) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un local del sistema según su identificador único
     *
     * @param idEvento El identificador único del evento que se va a eliminar
     * @throws EntityNotFoundException   Si no se encuentra ningún evento con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema que impide la eliminación del evento.
     */
    void delete(Long idEvento) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Añade un plato a un evento existente.
     *
     * @param idEvento El ID del evento al que se desea añadir el plato.
     * @param idPlato  El ID del plato que se desea asociar al evento.
     * @return El evento actualizado con el plato añadido.
     * @throws EntityNotFoundException   Si el evento o el plato con los IDs especificados no se encuentran en la base de datos.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la asociación del plato al evento.
     */
    Evento addPlato(Long idEvento, Long idPlato) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Añade una decoracion a un evento existente
     *
     * @param idEvento     El ID del evento al que se desea añadir el plato.
     * @param idDecoracion El ID de la decoracion que se desea asociar al evento.
     * @return El evento actualizado con la decoracion añadido
     * @throws EntityNotFoundException   Si el evento o el plato con los IDs especificados no se encuentran en la base de datos.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante la asociación del plato al evento.
     */
    Evento addDecoracionToEvento(Long idEvento, Long idDecoracion) throws EntityNotFoundException, IllegalOperationException;

}