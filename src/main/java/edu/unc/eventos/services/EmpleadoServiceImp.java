/**
 * @file: EmpleadoServiceImp.java
 * @author: (c)2024 Yeison García
 * @created: Mar 04, 2024 01:43:76 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.EmpleadoRepository;
import edu.unc.eventos.repositories.RolRepository;
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

    @Autowired
    private RolRepository rolRepository;

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
            if (empleadoDB.getDni().equals(empleado.getDni())) {
                throw new IllegalOperationException("Un empleado con el DNI ingresado ya existe en la base de datos.");
            }
            if (empleadoDB.getEmail().equals(empleado.getEmail())) {
                throw new IllegalOperationException("El email ya existe en la base de datos.");
            }
            if (empleadoDB.getTelefono().equals(empleado.getTelefono())) {
                throw new IllegalOperationException("El teléfono ya existe en la base de datos.");
            }
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
        if (empleadoDB != null) {
            if (empleadoDB.getDni().equals(empleado.getDni())) {
                throw new IllegalOperationException("Un empleado con el DNI ingresado ya existe en la base de datos.");
            }
            if (empleadoDB.getEmail().equals(empleado.getEmail())) {
                throw new IllegalOperationException("El email ya existe en la base de datos.");
            }
            if (empleadoDB.getTelefono().equals(empleado.getTelefono())) {
                throw new IllegalOperationException("El teléfono ya existe en la base de datos.");
            }
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
        if (!empleado.getEmpleados_supervisados().isEmpty()) {
            throw new IllegalOperationException("El empleado es supervisor de otros empleados.");
        }
        empleadoRepository.delete(empleado);
    }

    /**
     * Asigna un rol a un empleado.
     *
     * @param idEmpleado ID del empleado al que se asignará el rol
     * @param idRol      ID del rol que se asignará al empleado
     * @return El empleado actualizado con el rol asignado
     * @throws EntityNotFoundException   Si no se encuentra el empleado o el rol
     * @throws IllegalOperationException Si el empleado ya tiene asignado el rol proporcionado
     */
    @Override
    @Transactional
    public Empleado addRolToEmpleado(Long idEmpleado, Long idRol) throws EntityNotFoundException, IllegalOperationException {
        Empleado empleado = empleadoRepository.findById(idEmpleado)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con el ID proporcionado"));

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el ID proporcionado"));

        // Verificar si el empleado ya tiene asignado el rol
        if (empleado.getRol() != null) {
            throw new IllegalOperationException("El empleado ya tiene asignado el rol proporcionado");
        }

        empleado.setRol(rol); // Asignar el rol al empleado
        empleadoRepository.save(empleado); // Guardar los cambios en el empleado
        rolRepository.save(rol); // Guardar los cambios en el rol

        return empleado; // Devolver el empleado actualizado
    }

}
