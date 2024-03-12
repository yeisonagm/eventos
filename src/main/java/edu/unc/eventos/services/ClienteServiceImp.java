/**
 * @file: ClienteServiceImp.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 04, 2024 0:15:00 AM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Esta clase es la implementación concreta de la interfaz ClienteService.
 * Se encarga de la lógica de negocio relacionada con las entidades de tipo Cliente.
 * Las operaciones incluyen la obtención de todos los clientes, la búsqueda de un cliente por su ID,
 * la creación de un nuevo cliente, la actualización de un cliente existente y la eliminación de un cliente.
 * La anotación @Service indica que esta clase es un componente de servicio en la capa de negocio.
 * Spring Boot utiliza esta anotación para realizar la inyección de dependencias automáticamente.
 */
@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Devuelve todos los clientes que hay en la base de datos.
     *
     * @return Lista de entidades de tipo cliente.
     */
    @Override
    @Transactional
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    /**
     * Devuelve un cliente por su id
     *
     * @param idCliente Id del cliente que se quiere buscar.
     * @return El cliente que se encontró.
     * @throws EntityNotFoundException Si el cliente no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public Cliente getById(Long idCliente) throws EntityNotFoundException {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new EntityNotFoundException("El cliente con el ID proporcionado no se encontró.");
        }
        return clienteOpt.get();
    }

    /**
     * Guarda un nuevo cliente
     *
     * @param cliente Objeto del tipo Cliente que se va a persistir.
     * @return El objeto luego de guardarlo en la base de datos
     * @throws IllegalOperationException Si el documento de identidad del cliente ya existe.
     */
    @Override
    @Transactional
    public Cliente save(Cliente cliente) throws IllegalOperationException {
        if (clienteRepository.findByDi(cliente.getDi()) != null) {
            throw new IllegalOperationException("El cliente con el mismo documento de identidad ya existe.");
        }
        return clienteRepository.save(cliente);
    }

    /**
     * Actualiza un cliente existente
     *
     * @param idCliente Id del cliente que se quiere actualizar.
     * @param cliente   Objeto del tipo Cliente que se va a actualizar.
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException   Si el cliente no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el documento de identidad ya pertenece a otro cliente.
     */
    @Override
    @Transactional
    public Cliente update(Long idCliente, Cliente cliente) throws EntityNotFoundException, IllegalOperationException {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new EntityNotFoundException("El cliente con el Id proporcionado no se encontró.");
        }
        Cliente clienteConNuevoDi = clienteRepository.findByDi(cliente.getDi());
        if (clienteConNuevoDi != null) {
            throw new IllegalOperationException("El documento de identidad ya se encuentra registrado.");
        }

        cliente.setIdCliente(idCliente);
        return clienteRepository.save(cliente);
    }

    /**
     * Elimina un cliente existente
     *
     * @param idCliente Id del cliente que se quiere eliminar.
     * @throws EntityNotFoundException   Si el cliente no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el cliente tiene eventos asociados.
     */
    @Override
    @Transactional
    public void delete(Long idCliente) throws EntityNotFoundException, IllegalOperationException {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(
                () -> new EntityNotFoundException("El cliente con el ID proporcionado no se encontró.")
        );
        if (!cliente.getEventos().isEmpty()) {
            throw new IllegalOperationException("El cliente tiene eventos asociados.");
        }
        clienteRepository.deleteById(idCliente);
    }

    /**
     * Obtiene todos los eventos asociados a un cliente por su identificador.
     *
     * @param idCliente Identificador del cliente.
     * @return Lista de eventos asociados al cliente.
     * @throws EntityNotFoundException Si el cliente no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public List<Evento> getAllEventosByIdCliente(Long idCliente) throws EntityNotFoundException {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID: " + idCliente));

        return cliente.getEventos();
    }

    @Override
    public Long getNextClienteId(Long id) {
        List<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));
        for (int i = 0; i < clientes.size() - 1; i++) {
            if (clientes.get(i).getIdCliente().equals(id)) {
                return clientes.get(i + 1).getIdCliente();
            }
        }
        return null; // Si no hay cliente siguiente, retorna null
    }

    @Override
    public Long getPreviousClienteId(Long id) {
        List<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));
        for (int i = 1; i < clientes.size(); i++) {
            if (clientes.get(i).getIdCliente().equals(id)) {
                return clientes.get(i - 1).getIdCliente();
            }
        }
        return null; // Si no hay cliente anterior, retorna null
    }

    @Override
    public Long getFirstClienteId() {
        List<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));
        if (!clientes.isEmpty()) {
            return clientes.get(0).getIdCliente();
        }
        return null; // Si no hay clientes, retorna null
    }

    @Override
    public Long getLastClienteId() {
        List<Cliente> clientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC, "idCliente"));
        if (!clientes.isEmpty()) {
            return clientes.get(clientes.size() - 1).getIdCliente();
        }
        return null; // Si no hay clientes, retorna null
    }

}
