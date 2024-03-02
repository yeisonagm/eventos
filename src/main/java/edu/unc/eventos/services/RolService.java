package edu.unc.eventos.services;

import edu.unc.eventos.domain.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> getAllRol();

    Optional<Rol> getRolById(Long idRol);

    Rol saveRol(Rol rol);

    void deleteRol(Long idRol);
}
