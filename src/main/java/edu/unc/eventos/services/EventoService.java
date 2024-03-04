/**
 * @file: EventoService.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 10:39:39 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;
import java.util.Optional;

public interface EventoService {
    List<Evento> getAll();

    Optional<Evento> getEventoById(Long idEvento) throws EntityNotFoundException;

    Evento save(Evento evento) throws EntityNotFoundException;

    Evento update(Long idEvento, Evento evento) throws EntityNotFoundException, IllegalOperationException;

    void delete(Long idEvento) throws EntityNotFoundException, IllegalOperationException;
}