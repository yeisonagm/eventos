/**
 * @file: ClienteRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:28:00 PM
 * Repository para la entidad Cliente. Proporciona métodos de consulta personalizados para la clase Cliente.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca clientes por nombre.
     *
     * @param nombre El nombre del cliente.
     * @return Lista de clientes con el nombre proporcionado.
     */
    List<Cliente> findByNombre(String nombre);

    /**
     * Busca un cliente por documento de identidad.
     *
     * @param di El documento de identidad del cliente.
     * @return El cliente con el documento de identidad proporcionado.
     */
    Cliente findByDi(String di);

    /**
     * Busca clientes por dirección.
     *
     * @param direccion La dirección del cliente.
     * @return Lista de clientes con la dirección proporcionada.
     */
    List<Cliente> findByDireccion(String direccion);

    /**
     * Busca un cliente por número de teléfono.
     *
     * @param telefono El número de teléfono del cliente.
     * @return El cliente con el número de teléfono proporcionado.
     */
    Cliente findByTelefono(String telefono);
}
