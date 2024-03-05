/**
 * @file: EventoServiceImp.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 11:47:06 AM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Local;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.EventoRepository;
import edu.unc.eventos.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventoServiceImp implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private LocalRepository localRepository;

    /**
     * Este método devuelve una lista de todos los eventos
     *
     * @return Lista de entidades de tipo evento.
     */
    @Override
    @Transactional
    public List<Evento> getAll() {
        return eventoRepository.findAll();
    }

    /**
     * Devuelve un evento por su Id
     *
     * @param idEvento Identificador del Evento que se quiere busacar
     * @return El evento que se encontró
     * @throws EntityNotFoundException Si el evento no se encuentra en la base de datos
     */
    @Override
    @Transactional
    public Evento getEventoById(Long idEvento) throws EntityNotFoundException {
        Optional<Evento> evento = eventoRepository.findById(idEvento);
        if (evento.isEmpty()) {
            throw new EntityNotFoundException("El Evento con el ID proporcionado no se encontró.");
        }
        return evento.get();
    }

    /**
     * Guarda un nuevo evento
     *
     * @param evento Objeto del tipo Evento que se va a persistir
     * @return El objeto luego de guardarlo en la DB
     * @throws IllegalOperationException Si el nombre del evento o la fecha es inválido
     */
    @Override
    @Transactional
    public Evento save(Evento evento) throws IllegalOperationException {
        if (evento.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre del evento no puede estar vacío.");
        }
        if (evento.getFecha() == null) {
            throw new IllegalOperationException("La fecha del evento no puede estar vacía.");
        }
        if (existsEventOnSameDayAndLocal(evento.getLocal(), evento.getFecha())) {
            throw new IllegalOperationException("Ya hay un evento planificado en el mismo local para la misma fecha.");
        }
        return eventoRepository.save(evento);
    }

    /**
     * Actualiza un evento eistente
     *
     * @param idEvento Id del evento que se quiere actualizar
     * @param evento   Objeto del tipo evento que se va a actualizar
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException   Si el evento no se encuentra en la base de datos
     * @throws IllegalOperationException Si el nombre o fecha del evento es inválido.
     */
    @Override
    @Transactional
    public Evento update(Long idEvento, Evento evento) throws EntityNotFoundException, IllegalOperationException {
        Optional<Evento> eventoOpt = eventoRepository.findById(idEvento);

        if (eventoOpt.isEmpty()) {
            throw new EntityNotFoundException("El evento con el id proporcionado no fue encontrado");
        }
        if (evento.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre del evento no puede estar vacío.");
        }
        if (evento.getFecha() == null) {
            throw new IllegalOperationException("La fecha del evento no puede estar vacía.");
        }
        if (existsEventOnSameDayAndLocal(evento.getLocal(), evento.getFecha())) {
            throw new IllegalOperationException("Ya hay un evento planificado en el mismo local para la misma fecha.");
        }

        evento.setIdEvento(idEvento);
        return eventoRepository.save(evento);
    }

    /**
     * Elimina un evento existente
     *
     * @param idEvento Id del evento que se quiere eliminar
     * @throws EntityNotFoundException   Si el evento no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el evento tiene un cliente, un local o un empleado asociado.
     */
    @Override
    @Transactional
    public void delete(Long idEvento) throws EntityNotFoundException, IllegalOperationException {

        Evento evento = eventoRepository.findById(idEvento).orElseThrow(
                () -> new EntityNotFoundException("El evento con el ID proporcionado no se encontró")
        );
        if (evento.getCliente() != null) {
            throw new IllegalOperationException("El evento tiene un cliente asociado.");
        }
        if (evento.getEmpleado() != null) {
            throw new IllegalOperationException("El evento tiene un empleado asociado.");
        }
        if (evento.getLocal() != null) {
            throw new IllegalOperationException("El evento tiene un local asociado.");
        }
        eventoRepository.deleteById(idEvento);
    }

    /**
     * Asigna un local a un evento existente
     *
     * @param idEvento Identificador único del evento al que se asignará el local.
     * @param idLocal  Identificador único del local el cual se asignará al evento.
     * @return
     * @throws EntityNotFoundException   Si el evento o el local no se encuentra en la base de datos.
     * @throws IllegalOperationException Si existe algún conflicto con la fecha en la que se da el evento.
     */
    @Override
    @Transactional
    public Evento addLocalToEvento(Long idEvento, Long idLocal) throws EntityNotFoundException, IllegalOperationException {
        Evento evento = eventoRepository.findById(idEvento).orElseThrow(
                () -> new EntityNotFoundException("El evento con el ID proporcionado no se encontró")
        );
        Local local = localRepository.findById(idLocal).orElseThrow(
                () -> new EntityNotFoundException("El local con el ID proporcionado no se encontró")
        );

        if (existsEventOnSameDayAndLocal(local, evento.getFecha())) {
            throw new IllegalOperationException("Ya hay un evento planificado en el mismo local para la misma fecha.");
        }

        evento.setLocal(local);
        local.getEventos().add(evento);
        return (eventoRepository.save(evento));
    }


    /**
     * Este método verifica si existe un Evento programdado en el mismo local, en el mismo día
     *
     * @param local Objeto del tipo local
     * @param fecha Objeto del tipo Date
     * @return Retorna un true o false de acuerdo a si existe un evento programado en un local, en la misma fecha que otro evento.
     */
    private boolean existsEventOnSameDayAndLocal(Local local, Date fecha) {
        LocalDate fechaEvento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Evento> eventosEnMismoDiaYLocal = eventoRepository.findByLocalAndFecha(local, fechaEvento);
        return !eventosEnMismoDiaYLocal.isEmpty();
    }
}
