/**
 * @file: RolService.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 10:32:12 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> getAll();

    Optional<Rol> getRolById(Long idRol) throws EntityNotFoundException;

    Rol save(Rol rol) throws EntityNotFoundException;

    Rol update(Long idRol, Rol rol) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idRol) throws EntityNotFoundException, IllegalOperationException;

}
