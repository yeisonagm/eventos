/**
 * @file: LocalRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:50:00 PM
 * Repository para la entidad Local. Proporciona métodos de consulta personalizados para la clase Local.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LocalRepository extends JpaRepository<Local, Long> {

    /**
     * Busca locales por ubicación.
     *
     * @param ubicacion La ubicación del local.
     * @return Lista de locales con la ubicación proporcionada.
     */
    List<Local> findByUbicacion(String ubicacion);

    /**
     * Busca locales por aforo entre dos números.
     *
     * @param aforoMin Aforo mínimo del local.
     * @param aforoMax Aforo máximo del local.
     * @return Lista de locales dentro del rango de aforo especificado.
     */
    List<Local> findByAforoBetween(Integer aforoMin, Integer aforoMax);

    /**
     * Busca locales por nombre.
     *
     * @param nombre El nombre del local.
     * @return Lista de locales con el nombre proporcionado.
     */
    List<Local> findByNombre(String nombre);
}
