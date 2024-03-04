/**
 * @file: SeguroRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 6:10:00 PM
 * Repository para la entidad Seguro. Proporciona métodos de consulta personalizados para la clase Seguro.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {

    /**
     * Busca un seguro por código.
     *
     * @param codigo El código del seguro.
     * @return El seguro con el código proporcionado.
     */
    Seguro findByCodigo(String codigo);

    /**
     * Busca seguros por fecha de inscripción antes de una fecha específica.
     *
     * @param fecha La fecha límite.
     * @return Lista de seguros con fecha de inscripción antes de la fecha proporcionada.
     */
    List<Seguro> findByFechaInscripcionBefore(Date fecha);

    /**
     * Busca seguros por fecha de inscripción entre dos fechas.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin    La fecha de fin del rango.
     * @return Lista de seguros con fecha de inscripción dentro del rango proporcionado.
     */
    List<Seguro> findByFechaInscripcionBetween(Date fechaInicio, Date fechaFin);
}
