package edu.unc.eventos.services;

import edu.unc.eventos.domain.Seguro;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;


public interface SeguroService {
    List<Seguro> getAll();

    Seguro getById(Long idSeguro) throws EntityNotFoundException;

    Seguro save(Seguro seguro) throws IllegalOperationException;

    Seguro update(Long idSeguro, Seguro seguro) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idSeguro) throws EntityNotFoundException, IllegalOperationException;
}
