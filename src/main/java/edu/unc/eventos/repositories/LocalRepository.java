package edu.unc.eventos.repositories;


import edu.unc.eventos.domain.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}
