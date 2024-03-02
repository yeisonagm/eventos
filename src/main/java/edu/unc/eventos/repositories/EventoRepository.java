package edu.unc.eventos.repositories;


import edu.unc.eventos.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
