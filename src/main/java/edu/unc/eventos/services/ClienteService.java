/**
 * @file: ClienteServiceImp.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 04, 2024 6:10:00 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de clientes en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de clientes.
 */
public interface ClienteService {

    /**
     * Recupera todos los clientes disponibles.
     *
     * @return Una lista que contiene todos los clientes disponibles.
     */
    List<Cliente> getAll();

    /**
     * Recupera un cliente por su identificador único.
     *
     * @param idCliente El identificador único del cliente a recuperar.
     * @return El cliente correspondiente al identificador proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún cliente con el identificador especificado.
     */
    Cliente getById(Long idCliente) throws EntityNotFoundException;

    /**
     * Guarda un nuevo cliente.
     *
     * @param cliente El cliente que se va a guardar.
     * @return El cliente guardado en el sistema.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de guardado del cliente.
     */
    Cliente save(Cliente cliente) throws IllegalOperationException;

    /**
     * Actualiza un cliente existente.
     *
     * @param idCliente El identificador único del cliente a actualizar.
     * @param cliente   El objeto Cliente con los nuevos datos del cliente.
     * @return El cliente actualizado.
     * @throws EntityNotFoundException  Si no se encuentra ningún cliente con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de actualización del cliente.
     */
    Cliente update(Long idCliente, Cliente cliente) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un cliente del sistema.
     *
     * @param idCliente El identificador único del cliente a eliminar.
     * @throws EntityNotFoundException  Si no se encuentra ningún cliente con el identificador especificado.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de eliminación del cliente.
     */
    void delete(Long idCliente) throws EntityNotFoundException, IllegalOperationException;



}
