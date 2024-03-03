package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByDi(String di);
    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByTelefono(String telefono);

}
