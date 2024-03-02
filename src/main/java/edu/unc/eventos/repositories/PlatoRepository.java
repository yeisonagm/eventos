package edu.unc.eventos.repositories;


import edu.unc.eventos.domain.Plato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
}
