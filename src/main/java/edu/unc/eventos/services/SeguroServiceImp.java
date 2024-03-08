/**
 * @file: SeguroServiceImp.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 04, 2024 0:20:00 AM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Seguro;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.EmpleadoRepository;
import edu.unc.eventos.repositories.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Esta clase es la implementación concreta de la interfaz SeguroService.
 * <p>Se encarga de la lógica de negocio relacionada con las entidades de tipo Seguro.
 * Las operaciones incluyen la obtención de todos los seguros, la búsqueda de un seguro por su ID,
 * la creación de un nuevo seguro, la actualización de un seguro existente y la eliminación de un seguro.</p>
 * <p>
 * La anotación @Service indica que esta clase es un componente de servicio en la capa de negocio.
 * Spring Boot utiliza esta anotación para realizar la inyección de dependencias automáticamente.
 */
@Service
public class SeguroServiceImp implements SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Devuelve todos los seguros que hay en la base de datos.
     *
     * @return Lista de entidades de tipo seguro.
     */
    @Override
    @Transactional
    public List<Seguro> getAll() {
        return seguroRepository.findAll();
    }

    /**
     * Devuelve un seguro por su id
     *
     * @param idSeguro Id del seguro que se quiere buscar.
     * @return El seguro que se encontró.
     * @throws EntityNotFoundException Si el seguro no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public Seguro getById(Long idSeguro) throws EntityNotFoundException {
        Optional<Seguro> seguroOpt = seguroRepository.findById(idSeguro);
        if (seguroOpt.isEmpty()) {
            throw new EntityNotFoundException("El seguro con el ID proporcionado no se encontró.");
        }
        return seguroOpt.get();
    }

    /**
     * Guarda un nuevo seguro
     *
     * @param seguro Objeto del tipo Seguro que se va a persistir.
     * @return El objeto luego de guardarlo en la base de datos
     * @throws EntityNotFoundException Si ya existe un seguro con el mismo ID o código.
     */
    @Override
    @Transactional
    public Seguro save(Seguro seguro) throws EntityNotFoundException, IllegalOperationException {
        if (seguro.getCodigo().isEmpty()) {
            throw new EntityNotFoundException("El código no puede estar vacío.");
        }
        if (seguro.getFechaInscripcion() == null) {
            throw new IllegalOperationException("La fecha de inscripción no puede estar vacía.");
        } else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1); // Resta un mes a la fecha actual
            Date minDate = c.getTime(); // Obtiene la fecha mínima permitida
            if (seguro.getFechaInscripcion().before(minDate)) {
                throw new IllegalOperationException("La fecha de inscripción no puede ser más de un mes en el pasado.");
            }
        }

        if (seguroRepository.findByCodigo(seguro.getCodigo()) != null) {
            throw new EntityNotFoundException("Ya existe un seguro con el mismo código.");
        }

        return seguroRepository.save(seguro);
    }

    /**
     * Actualiza un seguro existente
     *
     * @param idSeguro Id del seguro que se quiere actualizar.
     * @param seguro   Objeto del tipo Seguro que se va a actualizar.
     * @return El objeto luego de actualizarlo en la base de datos
     * @throws EntityNotFoundException   Si el seguro no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el seguro ya pertenece a otro código.
     */
    @Override
    @Transactional
    public Seguro update(Long idSeguro, Seguro seguro) throws EntityNotFoundException, IllegalOperationException {
        Optional<Seguro> seguroOpt = seguroRepository.findById(idSeguro);
        if (seguroOpt.isEmpty()) {
            throw new EntityNotFoundException("El seguro con id proporcionado no fue encontrado");
        }

        if (seguro.getFechaInscripcion() == null) {
            throw new IllegalOperationException("La fecha de inscripción no puede estar vacía.");
        } else {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1); // Resta un mes a la fecha actual
            Date minDate = c.getTime(); // Obtiene la fecha mínima permitida
            if (seguro.getFechaInscripcion().before(minDate)) {
                throw new IllegalOperationException("La fecha de inscripción no puede ser más de un mes en el pasado.");
            }
        }

        Seguro seguroConNuevoCodigo = seguroRepository.findByCodigo(seguro.getCodigo());
        if (seguroConNuevoCodigo != null && !seguroConNuevoCodigo.getIdSeguro().equals(idSeguro)) {
            throw new IllegalOperationException("El seguro ya pertenece a otro código.");
        }

        seguro.setIdSeguro(idSeguro);
        return seguroRepository.save(seguro);
    }

    /**
     * Elimina un seguro existente
     *
     * @param idSeguro Id del seguro que se quiere eliminar.
     * @throws EntityNotFoundException   Si el seguro no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el seguro está asociado a un empleado y no se puede eliminar.
     */
    @Override
    @Transactional
    public void delete(Long idSeguro) throws EntityNotFoundException, IllegalOperationException {
        Seguro seguro = seguroRepository.findById(idSeguro).orElseThrow(
                () -> new EntityNotFoundException("El seguro con el ID proporcionado no se encontró.")
        );
        if (seguro.getEmpleado() != null) {
            throw new EntityNotFoundException("El seguro está asociado a un empleado y no se puede eliminar.");
        }

        seguroRepository.deleteById(idSeguro);
    }

    /**
     * Agrega un empleado a un seguro.
     *
     * @param idSeguro   El identificador único del seguro al que se le va a agregar el empleado.
     * @param idEmpleado El identificador único del empleado que se va a agregar al seguro.
     * @return El seguro con el empleado agregado.
     * @throws EntityNotFoundException   Si no se encuentra ningún seguro o empleado con los identificadores especificados.
     * @throws IllegalOperationException Si ocurre una operación ilegal durante el proceso de agregado del empleado al seguro.
     */
    @Override
    public Seguro addEmpleado(Long idSeguro, Long idEmpleado) throws EntityNotFoundException, IllegalOperationException {
        Seguro seguro = seguroRepository.findById(idSeguro)
                .orElseThrow(() -> new EntityNotFoundException("Seguro con el Id proporcionado no existe."));
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado con el Id proporcionado no existe."));

        if (empleado.getSeguro() != null) {
            throw new IllegalOperationException("El empleado ya tiene asignado un seguro. Seguro: "
                    + empleado.getSeguro().getCodigo());
        }

        seguro.setEmpleado(empleado);
        return seguroRepository.save(seguro);
    }
}
