/**
 * @file: EventoService.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 10:39:39 PM
 * @description: Interfaz que define los servicios disponibles para la gestión de eventos en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de eventos.
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
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
     * Recupera un evento específico según su ID
     *
     * @param idEvento Idetificador único del evento.
     * @return
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
     * @param evento El objeto Evento con los nuevos datos
     * @return El objeto Evento actualizado
     * @throws EntityNotFoundException Si no se encuentra ningún evento con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema con los datos del evento que impide su actualización.
     */
    Evento update(Long idEvento, Evento evento) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un local del sistema según su identificador único
     *
     * @param idEvento El identificador único del evento que se va a eliminar
     * @throws EntityNotFoundException Si no se encuentra ningún evento con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema que impide la eliminación del evento.
     */
    void delete(Long idEvento) throws EntityNotFoundException, IllegalOperationException;
}