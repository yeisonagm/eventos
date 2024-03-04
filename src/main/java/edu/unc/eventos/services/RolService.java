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

    Rol getRolById(Long idRol) throws EntityNotFoundException;

    Rol save(Rol rol) throws IllegalOperationException;

    Rol update(Long idRol, Rol rol) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idRol) throws EntityNotFoundException, IllegalOperationException;

}
