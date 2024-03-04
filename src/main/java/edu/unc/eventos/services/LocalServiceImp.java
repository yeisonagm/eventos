package edu.unc.eventos.services;


import edu.unc.eventos.domain.Local;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocalServiceImp implements LocalService {
    @Autowired
    private LocalRepository localRepository;

    /**
     * Devuelve todos los locales que hay en la base de datos.
     *
     * @return Lista de entidades de tipo local.
     */
    @Override
    @Transactional
    public List<Local> getAll() {
        return localRepository.findAll();
    }

    /**
     * Devuelve un local por su id
     *
     * @param idLocal Id del local que se quiere buscar.
     * @return El local que se encontró.
     * @throws EntityNotFoundException Si el local no se encuentra en la base de datos.
     */
    @Override
    @Transactional(readOnly = true)
    public Local getById(Long idLocal)throws EntityNotFoundException{
        Optional<Local> localOpt = localRepository.findById(idLocal);
        if (localOpt.isEmpty()) {
            throw new EntityNotFoundException("El local con el ID proporcionado no se encontró.");
        }
        return localOpt.get();
    }

    /**
     * Guarda un objeto Local en el sistema.
     *
     * Este método guarda un objeto Local en la base de datos. Realiza validaciones previas
     * para asegurar que los datos del local sean correctos.
     *
     * @param local El objeto Local que se va a guardar.
     * @return El objeto Local guardado en la base de datos.
     * @throws IllegalOperationException Si el nombre del local está vacío o si el aforo del local es menor o igual a cero.
     */
    @Override
    @Transactional
    public Local save(Local local) throws IllegalOperationException {
        if (local.getNombre().isEmpty()) {
            throw new IllegalOperationException("El nombre del local no puede estar vacio");
        }
        if (local.getAforo() <= 0) {
            throw new IllegalOperationException("El aforo del local no puede ser menor o igual a cero.");
        }
        if (localRepository.findByNombre(local.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del local ya existe en la base de datos");
        }
        if (localRepository.findByUbicacion(local.getUbicacion()).isEmpty()) {
            throw new IllegalOperationException("La dirección del local ya existe en la base de datos");
        }
        return localRepository.save(local);
    }

    /**
     * Actualiza un local existente
     *
     * @param idLocal Id del Local que se quiere actualizar.
     * @param local   Objeto del tipo Local que se va a actualizar.
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException    Si el local no se encuentra en la base de datos.
     */
    @Override
    public Local update(Long idLocal, Local local) throws EntityNotFoundException, IllegalOperationException {
        Optional<Local> localOpt = localRepository.findById(idLocal);
        if (localOpt.isEmpty()) {
            throw new EntityNotFoundException("El local con el id proporcionado no fue encontrado");
        }
        if (!localRepository.findByNombre(local.getNombre()).isEmpty()) {
            throw new IllegalOperationException("El nombre del local ya existe en la base de datos");
        }
        if (!localRepository.findByUbicacion(local.getUbicacion()).isEmpty()) {
            throw new IllegalOperationException("La dirección del local ya existe en la base de datos");
        }
        local.setIdLocal(idLocal);
        return localRepository.save(local);
    }

    /**
     * Elimina un local existente
     *
     * @param idLocal Id del local que se quiere eliminar.
     * @throws EntityNotFoundException   Si el local no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el local tiene eventos asociados.
     */
    @Override
    public void delete(Long idLocal) throws IllegalOperationException {
        Local local = localRepository.findById(idLocal).orElseThrow(
                () -> new EntityNotFoundException("El local con el ID proporcionado no se encontró.")
        );
        if (!local.getEventos().isEmpty()) {
            throw new IllegalOperationException("El local tiene eventos asociados.");
        }
        localRepository.deleteById(idLocal);
    }
}
