package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
}
