package edu.unc.eventos.services;

import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> getAllClientes() {

        return (List<Cliente>) clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteById(Long idCliente) {

        return clienteRepository.findById(idCliente);
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }
}
