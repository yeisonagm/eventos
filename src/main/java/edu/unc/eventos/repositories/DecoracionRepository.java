/**
 * @file: DecoracionRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:35:00 PM
 * Repository para la entidad Decoracion. Proporciona métodos de consulta personalizados para la clase Decoracion.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Decoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DecoracionRepository extends JpaRepository<Decoracion, Long> {

    /**
     * Busca decoraciones por color.
     *
     * @param color El color de la decoración.
     * @return Lista de decoraciones con el color proporcionado.
     */
    List<Decoracion> findByColor(String color);

    /**
     * Busca decoraciones por rango de precio.
     *
     * @param minPrecio Precio mínimo.
     * @param maxPrecio Precio máximo.
     * @return Lista de decoraciones dentro del rango de precio especificado.
     */
    List<Decoracion> findByPrecioBetween(Double minPrecio, Double maxPrecio);
}
