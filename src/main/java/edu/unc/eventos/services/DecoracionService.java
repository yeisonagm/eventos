package edu.unc.eventos.services;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface DecoracionService {
    List<Decoracion> getAll();

    Decoracion getById(Long idDecoracion) throws EntityNotFoundException;

    Decoracion save(Decoracion decoracion) throws IllegalOperationException;

    Decoracion update(Long idDecoracion, Decoracion decoracion) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idDecoracion) throws EntityNotFoundException, IllegalOperationException;
}
