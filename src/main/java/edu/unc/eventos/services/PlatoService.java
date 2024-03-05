/**
 * @file: PlatoService.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 04, 2024 00:37:00 AM
 * @description: Interfaz que define los servicios disponibles para la gestión de platos en el sistema.
 *  Proporciona métodos para la recuperación, creación, actualización y eliminación de platos.
 */

package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de platos en el sistema.
 */
public interface PlatoService {

    /**
     * Recupera todos los platos disponibles.
     *
     * @return Una lista que contiene todos los platos disponibles.
     */
    List<Plato> getAll();

    /**
     * Obtiene una lista de eventos asociados a un plato específico.
     *
     * Recibe el ID del plato como parámetro y retorna una lista de eventos asociados a ese plato.
     *
     * @param platoId El ID del plato del cual se desean obtener los eventos.
     * @return Lista de eventos asociados al plato especificado.
     */
    List<Evento> getEventosByPlatoId(Long platoId);

    /**
     * Obtiene un evento específico asociado a un plato.

     * Recibe el ID del plato y el ID del evento como parámetros y retorna el evento asociado a ese plato.
     *
     * @param platoId El ID del plato del cual se desea obtener el evento.
     * @param eventoId El ID del evento que se desea recuperar.
     * @return El evento asociado al plato especificado.
     */
    Evento getEventoByPlatoId(Long platoId, Long eventoId);

    /**
     * Recupera un plato por su identificador único.
     *
     * @param idPlato El identificador único del plato a recuperar.
     * @return El plato correspondiente al identificador proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún plato con el identificador especificado.
     */
    Plato getById(Long idPlato) throws EntityNotFoundException;

    /**
     * Guarda un nuevo plato.
     *
     * @param plato El plato que se va a guardar.
     * @return El plato guardado en el sistema.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de guardado del plato.
     */
    Plato save(Plato plato) throws IllegalOperationException;

    /**
     * Actualiza un plato existente.
     *
     * @param idPlato El identificador único del plato a actualizar.
     * @param plato   El objeto Plato con los nuevos datos del plato.
     * @return El plato actualizado.
     * @throws EntityNotFoundException  Si no se encuentra ningún plato con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de actualización del plato.
     */
    Plato update(Long idPlato, Plato plato) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un plato del sistema.
     *
     * @param idPlato El identificador único del plato a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra ningún plato con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de eliminación del plato.
     */
    void delete(Long idPlato) throws EntityNotFoundException, IllegalOperationException;
}