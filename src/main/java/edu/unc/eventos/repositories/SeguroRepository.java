package edu.unc.eventos.repositories;


import edu.unc.eventos.domain.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}
