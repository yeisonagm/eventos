/**
 * @file: PlatoRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:55:00 PM
 * Repository para la entidad Plato. Proporciona métodos de consulta personalizados para la clase Plato.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Long> {

    /**
     * Busca platos por tipo.
     *
     * @param tipo El tipo de plato.
     * @return Lista de platos con el tipo proporcionado.
     */
    List<Plato> findByTipo(String tipo);

    /**
     * Busca un plato por nombre.
     *
     * @param nombre El nombre del plato.
     * @return El plato con el nombre proporcionado.
     */
    Plato findByNombre(String nombre);
}
