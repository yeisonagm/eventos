package edu.unc.eventos.services;

import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> getAll();

    Cliente getById(Long idCliente) throws EntityNotFoundException;

    Cliente save(Cliente cliente) throws IllegalOperationException;

    Cliente update(Long idCliente, Cliente cliente) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idCliente) throws EntityNotFoundException, IllegalOperationException;
}
