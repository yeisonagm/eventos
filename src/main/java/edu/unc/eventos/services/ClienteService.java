package edu.unc.eventos.services;

import edu.unc.eventos.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> getAllClientes();

    Optional<Cliente> getClienteById(Long idCliente);

    Cliente saveCliente(Cliente cliente);

    void deleteCliente(Long idCliente);
}
