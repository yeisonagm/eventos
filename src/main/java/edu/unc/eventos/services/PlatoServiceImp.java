package edu.unc.eventos.services;


import edu.unc.eventos.domain.Local;
import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImp implements PlatoService {

    @Autowired
    private PlatoRepository platoRepository;

    /**
     * Devuelve todos los platos que hay en la base de datos.
     *
     * @return Lista de entidades de tipo plato.
     */
    @Override
    @Transactional
    public List<Plato> getAll() {
        return platoRepository.findAll();
    }

    /**
     * Devuelve un plato por su id
     *
     * @param idPlato Id del plato que se quiere buscar.
     * @return El plato que se encontró.
     * @throws EntityNotFoundException Si el plato no se encuentra en la base de datos.
     */
    @Override
    @Transactional(readOnly = true)
    public Plato getById(Long idPlato)throws EntityNotFoundException{
        Optional<Plato> platoOpt = platoRepository.findById(idPlato);
        if (platoOpt.isEmpty()) {
            throw new EntityNotFoundException("El plato con el ID proporcionado no se encontró.");
        }
        return platoOpt.get();
    }

    /**
     * Guarda un objeto Plato en el sistema.
     *
     * Este método guarda un objeto Plato en la base de datos. Realiza validaciones previas
     * para asegurar que los datos del plato sean correctos.
     *
     * @param plato El objeto Plato que se va a guardar.
     * @return El objeto Plato guardado en la base de datos.
     * @throws IllegalOperationException Si el nombre del plato está vacío.
     */
    @Override
    @Transactional
    public Plato save(Plato plato) throws IllegalOperationException {
        if (plato.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre del plato no puede estar vacio");
        }
        if (platoRepository.findByNombre(plato.getNombre()) != null) {
            throw new IllegalOperationException("El nombre del local ya existe en la base de datos");
        }
        return platoRepository.save(plato);
    }

    /**
     * Actualiza un plato existente
     *
     * @param idPlato Id del Plato que se quiere actualizar.
     * @param plato   Objeto del tipo Plato que se va a actualizar.
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException    Si el plato no se encuentra en la base de datos.
     */
    @Override
    public Plato update(Long idPlato, Plato plato) throws EntityNotFoundException, IllegalOperationException {
        Optional<Plato> platoOpt = platoRepository.findById(idPlato);
        if (platoOpt.isEmpty()) {
            throw new EntityNotFoundException("El plato con el id proporcionado no fue encontrado");
        }
        if (platoRepository.findByNombre(plato.getNombre()) != null) {
            throw new IllegalOperationException("El nombre del local ya existe en la base de datos");
        }
        plato.setIdPlato(idPlato);
        return platoRepository.save(plato);
    }

    /**
     * Elimina un Plato existente
     *
     * @param idPlato Id del plato que se quiere eliminar.
     * @throws EntityNotFoundException   Si el plato no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el plato tiene eventos asociados.
     */
    @Override
    public void delete(Long idPlato) throws IllegalOperationException {
        Plato plato = platoRepository.findById(idPlato).orElseThrow(
                () -> new EntityNotFoundException("El plato con el ID proporcionado no se encontró.")
        );
        if (!plato.getEventos().isEmpty()) {
            throw new IllegalOperationException("El plato tiene eventos asociados.");
        }
        platoRepository.deleteById(idPlato);
    }
}
