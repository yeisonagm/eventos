/**
 * @file: SeguroService.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 04, 2024 6:10:00 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Seguro;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de seguros en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de seguros.
 */
public interface SeguroService {

    /**
     * Recupera todos los seguros disponibles.
     *
     * @return Una lista que contiene todos los seguros disponibles.
     */
    List<Seguro> getAll();

    /**
     * Recupera un seguro por su identificador único.
     *
     * @param idSeguro El identificador único del seguro a recuperar.
     * @return El seguro correspondiente al identificador proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún seguro con el identificador especificado.
     */
    Seguro getById(Long idSeguro) throws EntityNotFoundException;

    /**
     * Guarda un nuevo seguro.
     *
     * @param seguro El seguro que se va a guardar.
     * @return El seguro guardado en el sistema.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de guardado del seguro.
     */
    Seguro save(Seguro seguro) throws IllegalOperationException;

    /**
     * Actualiza un seguro existente.
     *
     * @param idSeguro El identificador único del seguro a actualizar.
     * @param seguro   El objeto Seguro con los nuevos datos del seguro.
     * @return El seguro actualizado.
     * @throws EntityNotFoundException  Si no se encuentra ningún seguro con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de actualización del seguro.
     */
    Seguro update(Long idSeguro, Seguro seguro) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un seguro del sistema.
     *
     * @param idSeguro El identificador único del seguro a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra ningún seguro con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de eliminación del seguro.
     */
    void delete(Long idSeguro) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Agrega un empleado a un seguro.
     *
     * @param idSeguro   El identificador único del seguro al que se le va a agregar el empleado.
     * @param idEmpleado El identificador único del empleado que se va a agregar al seguro.
     * @return El seguro con el empleado agregado.
     * @throws EntityNotFoundException  Si no se encuentra ningún seguro o empleado con los identificadores especificados.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de agregado del empleado al seguro.
     */
    Seguro addEmpleado(Long idSeguro, Long idEmpleado) throws EntityNotFoundException, IllegalOperationException;
}
