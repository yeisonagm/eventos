package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoService {
    List<Evento> getAllEvento();

    Optional<Evento> getEventoById(Long idEvento);

    Evento saveEvento(Evento evento);

    void deleteEvento(Long idEvento);
}