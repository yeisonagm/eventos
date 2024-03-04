/**
 * @file: EventoRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:45:00 PM
 * Repository para la entidad Evento. Proporciona métodos de consulta personalizados para la clase Evento.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    /**
     * Busca eventos por fecha.
     *
     * @param fecha La fecha del evento.
     * @return Lista de eventos en la fecha proporcionada.
     */
    List<Evento> findByFecha(Date fecha);

    /**
     * Busca eventos por duración.
     *
     * @param minDuracion Duración mínima del evento.
     * @param maxDuracion Duración máxima del evento.
     * @return Lista de eventos dentro del rango de duración especificado.
     */
    List<Evento> findByDuracionBetween(Integer minDuracion, Integer maxDuracion);

    List<Evento> findByLocalAndFecha(Local local, LocalDate fechaEvento);

}
