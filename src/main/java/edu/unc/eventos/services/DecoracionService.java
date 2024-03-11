/**
 * @file: EmpleadoService.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 12:15:56 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Esta interfaz define las operaciones que se pueden realizar sobre la entidad Decoracion.
 * <p>Las operaciones incluyen la obtención de todas las decoraciones, la búsqueda de una decoración por su ID,
 * la creación de una nueva decoración, la actualización de una decoración existente y la eliminación de una decoración.</p>
 */
public interface DecoracionService {
    /**
     * Devuelve todas las decoraciones que hay en la base de datos.
     *
     * @return Lista de entidades de tipo decoración.
     */
    List<Decoracion> getAll();

    /**
     * Devuelve una decoración por su id
     *
     * @param idDecoracion Id de la decoración que se quiere buscar.
     * @return La decoración que se encontró.
     * @throws EntityNotFoundException Si la decoración no se encuentra en la base de datos.
     */
    Decoracion getById(Long idDecoracion) throws EntityNotFoundException;

    /**
     * Crea una nueva decoración en la base de datos.
     *
     * @param decoracion La decoración que se quiere guardar.
     * @return La decoración que se creó.
     * @throws IllegalOperationException Si la decoración ya existe en la base de datos.
     */
    Decoracion save(Decoracion decoracion) throws IllegalOperationException;

    /**
     * Actualiza una decoración existente en la base de datos.
     *
     * @param idDecoracion Id de la decoración que se quiere actualizar.
     * @param decoracion   La decoración con los datos actualizados.
     * @return La decoración que se actualizó.
     * @throws EntityNotFoundException   Si la decoración no se encuentra en la base de datos.
     * @throws IllegalOperationException Si la decoración ya existe en la base de datos.
     */
    Decoracion update(Long idDecoracion, Decoracion decoracion) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina una decoración de la base de datos.
     *
     * @param idDecoracion Id de la decoración que se quiere eliminar.
     * @throws EntityNotFoundException   Si la decoración no se encuentra en la base de datos.
     * @throws IllegalOperationException Si la decoración no se puede eliminar.
     */
    void delete(Long idDecoracion) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Este metodo retorna una lista de Eventos los cuales tienen una misma decoracion
     *
     * @param idDecoracion Identificador de la decoración
     * @return La lista de eventos que se encontraron
     * @throws EntityNotFoundException Si el Identificador de la decoración no existe
     */
    List<Evento> getAllEventosByIdDecoracion(Long idDecoracion) throws EntityNotFoundException;

    /**
     * Este método retorna un evento específico con una decoración específica
     *
     * @param idDecoracion Identificador de la decoración
     * @param idEvento     Identificador de la decoración
     * @return El evento que se encontró
     * @throws EntityNotFoundException Si el Identificador de la decoración o del evento no existe
     */
    Evento getByIdDecoracionByIdEvento(Long idDecoracion, Long idEvento) throws EntityNotFoundException;
}
