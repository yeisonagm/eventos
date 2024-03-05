/**
 * @file: LocalService.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 04, 2024 00:37:00 AM
 * @description: Interfaz que define los servicios disponibles para la gestión de locales en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de locales.
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Interfaz que define las operaciones disponibles para gestionar objetos Local en el sistema.
 */
public interface LocalService {

    /**
     * Recupera todos los locales registrados.
     *
     * @return Lista de objetos Local.
     */
    List<Local> getAll();

    /**
     * Recupera un local específico según su identificador único.
     *
     * @param idLocal El identificador único del local.
     * @return El objeto Local correspondiente al identificador proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún local con el identificador especificado.
     */
    Local getById(Long idLocal) throws EntityNotFoundException;

    /**
     * Guarda un nuevo local.
     *
     * @param local El objeto Local que se va a guardar.
     * @return El objeto Local guardado.
     * @throws IllegalOperationException Si hay algún problema con los datos del local que impide su guardado.
     */
    Local save(Local local) throws IllegalOperationException;

    /**
     * Actualiza un local existente.
     *
     * @param idLocal El identificador único del local que se va a actualizar.
     * @param local El objeto Local con los nuevos datos.
     * @return El objeto Local actualizado.
     * @throws EntityNotFoundException Si no se encuentra ningún local con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema con los datos del local que impide su actualización.
     */
    Local update(Long idLocal, Local local) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un local del sistema según su identificador único.
     *
     * @param idLocal El identificador único del local que se va a eliminar.
     * @throws EntityNotFoundException Si no se encuentra ningún local con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema que impide la eliminación del local.
     */
    void delete(Long idLocal) throws EntityNotFoundException, IllegalOperationException;
}
