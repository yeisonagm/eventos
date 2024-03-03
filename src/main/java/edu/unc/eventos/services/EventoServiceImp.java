package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImp implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public List<Evento> getAllEvento() {
        return eventoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Evento> getEventoById(Long idEvento) {
        return eventoRepository.findById(idEvento);
    }

    @Override
    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public void deleteEvento(Long idEvento) {
        eventoRepository.deleteById(idEvento);
    }
}
