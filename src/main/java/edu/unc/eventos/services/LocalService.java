package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface LocalService {
    List<Local> getAll() ;

    Local getById(Long idLocal) throws EntityNotFoundException;

    Local save(Local local)throws IllegalOperationException;

    Local update(Long idLocal, Local local)throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idLocal) throws EntityNotFoundException, IllegalOperationException;
}
