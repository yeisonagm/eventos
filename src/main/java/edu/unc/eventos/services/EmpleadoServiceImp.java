/**
 * @file: EmpleadoServiceImp.java
 * @author: (c)2024 Yeison García
 * @created: Mar 04, 2024 01:43:76 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Esta clase es la implementación concreta de la interfaz EmpleadoService.
 * <p>Se encarga de la lógica de negocio relacionada con las entidades de tipo Empleado.
 * Las operaciones incluyen la obtención de todos los empleados, la búsqueda de un empleado por su ID,
 * la creación de un nuevo empleado, la actualización de un empleado existente y la eliminación de un empleado.</p>
 * <p>
 * La anotación @Service indica que esta clase es un componente de servicio en la capa de negocio.
 * Spring Boot utiliza esta anotación para realizar la inyección de dependencias automáticamente.
 */
@Service
public class EmpleadoServiceImp implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Devuelve todos los empleados que hay en la base de datos.
     *
     * @return Lista de entidades de tipo empleado.
     */
    @Override
    @Transactional
    public List<Empleado> getAll() {
        return empleadoRepository.findAll();
    }

    /**
     * Devuelve un empleado por su id
     *
     * @param idEmpleado Id del empleado que se quiere buscar.
     * @return El empleado que se encontró.
     * @throws EntityNotFoundException Si el empleado no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public Empleado getById(Long idEmpleado) throws EntityNotFoundException {
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(idEmpleado);
        if (empleadoOpt.isEmpty()) {
            throw new EntityNotFoundException("El empleado con el Id proporcionado no se encontró.");
        }
        return empleadoOpt.get();
    }

    /**
     * Crea un nuevo empleado en la base de datos.
     *
     * @param empleado El empleado que se quiere guardar.
     * @return El empleado que se creó.
     * @throws IllegalOperationException Si el empleado ya existe en la base de datos.
     */
    @Override
    @Transactional
    public Empleado save(Empleado empleado) throws IllegalOperationException {
        Empleado empleadoDB = empleadoRepository.findByDni(empleado.getDni());
        if (empleadoDB != null) {
            throw new IllegalOperationException("El empleado ya existe en la base de datos.");
        }
        return empleadoRepository.save(empleado);
    }

    /**
     * Actualiza un empleado existente en la base de datos.
     *
     * @param idEmpleado Id del empleado que se quiere actualizar.
     * @param empleado   El empleado con los datos actualizados.
     * @return El empleado que se actualizó.
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el empleado ya existe en la base de datos.
     */
    @Override
    @Transactional
    public Empleado update(Long idEmpleado, Empleado empleado) throws EntityNotFoundException, IllegalOperationException {
        Optional<Empleado> empleadoEntity = empleadoRepository.findById(idEmpleado);
        if (empleadoEntity.isEmpty()) {
            throw new EntityNotFoundException("El empleado con el Id proporcionado no se encontró.");
        }
        Empleado empleadoDB = empleadoRepository.findByDni(empleado.getDni());
        if (empleadoDB != null && empleadoDB.getIdEmpleado().equals(idEmpleado)) {
            throw new IllegalOperationException("El empleado ya existe en la base de datos.");
        }
        return empleadoRepository.save(empleado);
    }

    /**
     * Elimina un empleado de la base de datos.
     *
     * @param idEmpleado Id del empleado que se quiere eliminar.
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el empleado no puede ser eliminado.
     */
    @Override
    @Transactional
    public void delete(Long idEmpleado) throws EntityNotFoundException, IllegalOperationException {
        Empleado empleado = empleadoRepository.findById(idEmpleado).orElseThrow(
                () -> new EntityNotFoundException("El empleado con el Id proporcionado no se encontró."));
        if (!empleado.getEventos().isEmpty()) {
            throw new IllegalOperationException("El empleado tiene eventos asociados.");
        }
        if (!empleado.getEmpleados().isEmpty()) {
            throw new IllegalOperationException("El empleado es supervisor de otros empleados.");
        }
        empleadoRepository.delete(empleado);
    }
}
