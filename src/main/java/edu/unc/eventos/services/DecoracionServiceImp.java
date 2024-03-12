/**
 * @file: DecoracionServiceImp.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 11:03:06 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.DecoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Esta clase es la implementación concreta de la interfaz DecoracionService.
 * <p>Se encarga de la lógica de negocio relacionada con las entidades de tipo Decoracion.
 * Las operaciones incluyen la obtención de todas las decoraciones, la búsqueda de una decoración por su ID,
 * la creación de una nueva decoración, la actualización de una decoración existente y la eliminación de una decoración.</p>
 * <p>
 * La anotación @Service indica que esta clase es un componente de servicio en la capa de negocio.
 * Spring Boot utiliza esta anotación para realizar la inyección de dependencias automáticamente.
 */
@Service
public class DecoracionServiceImp implements DecoracionService {

    @Autowired
    private DecoracionRepository decoracionRepository;

    /**
     * Devuelve todas las decoraciones que hay en la base de datos.
     *
     * @return Lista de entidades de tipo decoración.
     */
    @Override
    @Transactional
    public List<Decoracion> getAll() {
        return decoracionRepository.findAll();
    }

    /**
     * Devuelve una decoración por su id
     *
     * @param idDecoracion Id de la decoración que se quiere buscar.
     * @return La decoración que se encontró.
     * @throws EntityNotFoundException Si la decoración no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public Decoracion getById(Long idDecoracion) throws EntityNotFoundException {
        Optional<Decoracion> decoracionOpt = decoracionRepository.findById(idDecoracion);
        if (decoracionOpt.isEmpty()) {
            throw new EntityNotFoundException("La decoración con el Id proporcionado no se encontró.");
        }
        return decoracionOpt.get();
    }

    /**
     * Guardar una nueva decoración
     *
     * @param decoracion Objeto del tipo Decoración que se va a persistir.
     * @return El objeto luego de guardarlo en la base de datos
     * @throws IllegalOperationException Si el nombre de decoración es inválido
     */
    @Override
    @Transactional
    public Decoracion save(Decoracion decoracion) throws IllegalOperationException {
        if (decoracion.getDescripcion().isEmpty()) {
            throw new IllegalOperationException("La descripción de la decoración no puede estar vacía.");
        }
        if (decoracion.getColor().isEmpty()) {
            throw new IllegalOperationException("El color de la decoración no puede estar vacío.");
        }
        return decoracionRepository.save(decoracion);
    }

    /**
     * Actualiza una decoración existente
     *
     * @param idDecoracion Id de la decoración que se quiere actualizar.
     * @param decoracion   Objeto del tipo Decoración que se va a actualizar.
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException Si la decoración no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public Decoracion update(Long idDecoracion, Decoracion decoracion) throws EntityNotFoundException {
        Optional<Decoracion> decoracionOpt = decoracionRepository.findById(idDecoracion);
        if (decoracionOpt.isEmpty()) {
            throw new EntityNotFoundException("La decoración con id proporcionado no fue encontrado");
        }
        decoracion.setIdDecoracion(idDecoracion);
        return decoracionRepository.save(decoracion);
    }

    /**
     * Elimina una decoración existente
     *
     * @param idDecoracion Id de la decoración que se quiere eliminar.
     * @throws EntityNotFoundException   Si la decoración no se encuentra en la base de datos.
     * @throws IllegalOperationException Si la decoración tiene eventos asociados.
     */
    @Override
    @Transactional
    public void delete(Long idDecoracion) throws EntityNotFoundException, IllegalOperationException {
        Decoracion decoracion = decoracionRepository.findById(idDecoracion).orElseThrow(
                () -> new EntityNotFoundException("La decoración con el ID proporcionado no se encontró.")
        );
        if (!decoracion.getEventos().isEmpty()) {
            throw new IllegalOperationException("La decoración tiene eventos asociados.");
        }

        decoracionRepository.deleteById(idDecoracion);
    }

    /**
     * Devuelve todos los eventos asociados a una decoración en específico
     *
     * @param idDecoracion Identificador de la decoración
     * @return Lista de eventos
     * @throws EntityNotFoundException Si el identificador de la decoración no es válido
     */
    @Override
    public List<Evento> getAllEventosByIdDecoracion(Long idDecoracion) throws EntityNotFoundException {
        Decoracion decoracion = decoracionRepository.findById(idDecoracion).orElseThrow(
                () -> new EntityNotFoundException("La decoración con el ID proporcionado no se encontró.")
        );
        return decoracion.getEventos();
    }

    /**
     * Devuelve un evento que tiene asociado una decoración en específico
     *
     * @param idDecoracion Identificador de la decoración
     * @param idEvento     Identificador de la decoración
     * @return Objeto del tipo Evento
     * @throws EntityNotFoundException Si el identificador de la decoración o el evento no es válido
     */
    @Override
    public Evento getByIdDecoracionByIdEvento(Long idDecoracion, Long idEvento) throws EntityNotFoundException {
        Decoracion decoracion = decoracionRepository.findById(idDecoracion).orElseThrow(
                () -> new EntityNotFoundException("La decoración con el ID proporcionado no se encontró.")
        );
        if (decoracion.getEventos() == null || decoracion.getEventos().isEmpty()) {
            throw new EntityNotFoundException("No se encontraron eventos asociados a la decoracion");
        }
        Optional<Evento> eventoOpt = decoracion.getEventos().stream()
                .filter(evento -> evento.getIdEvento().equals(idEvento))
                .findFirst();
        if (eventoOpt.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el evento asociado a la decoracion con el ID proporcionado");
        }

        return eventoOpt.get();
    }
}
