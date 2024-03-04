package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> getAll() ;

    Plato getById(Long idPlato) throws EntityNotFoundException;

    Plato save(Plato plato)throws IllegalOperationException;

    Plato update(Long idPlato, Plato plato)throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idPlato) throws EntityNotFoundException, IllegalOperationException;
}