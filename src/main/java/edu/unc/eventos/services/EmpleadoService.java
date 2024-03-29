/**
 * @file: EmpleadoService.java
 * @author: (c)2024 Yeison García
 * @created: Mar 04, 2024 01:03:06 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

/**
 * Esta interfaz define las operaciones que se pueden realizar sobre la entidad Empleado.
 * <p>Las operaciones incluyen la obtención de todos los empleados, la búsqueda de un empleado por su ID,
 * la creación de un nuevo empleado, la actualización de un empleado existente y la eliminación de un empleado.</p>
 */
public interface EmpleadoService {
    /**
     * Devuelve todos los empleados que hay en la base de datos.
     *
     * @return Lista de entidades de tipo empleado.
     */
    List<Empleado> getAll();

    /**
     * Devuelve un empleado por su id
     *
     * @param idEmpleado Id del empleado que se quiere buscar.
     * @return El empleado que se encontró.
     * @throws EntityNotFoundException Si el empleado no se encuentra en la base de datos.
     */
    Empleado getById(Long idEmpleado) throws EntityNotFoundException;

    /**
     * Crea un nuevo empleado en la base de datos.
     *
     * @param empleado El empleado que se quiere guardar.
     * @return El empleado que se creó.
     * @throws IllegalOperationException Si el empleado ya existe en la base de datos.
     */
    Empleado save(Empleado empleado) throws IllegalOperationException;

    /**
     * Actualiza un empleado existente en la base de datos.
     *
     * @param idEmpleado Id del empleado que se quiere actualizar.
     * @param empleado   El empleado con los datos actualizados.
     * @return El empleado que se actualizó.
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el empleado ya existe en la base de datos.
     */
    Empleado update(Long idEmpleado, Empleado empleado) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un empleado de la base de datos.
     *
     * @param idEmpleado Id del empleado que se quiere eliminar.
     * @throws EntityNotFoundException   Si el empleado no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el empleado no puede ser eliminado.
     */
    void delete(Long idEmpleado) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Asigna el supervisor de un empleado.
     *
     * @param idEmpleado   ID del empleado al que se asignará el supervisor
     * @param idSupervisor ID del supervisor que se asignará al empleado
     * @throws EntityNotFoundException   Si no se encuentra el empleado o el supervisor
     * @throws IllegalOperationException Si el empleado ya tiene asignado el supervisor proporcionado
     */
    Empleado addSupervisor(Long idEmpleado, Long idSupervisor) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Obtiene todos los empleados supervisados por un supervisor.
     *
     * @param idSupervisor ID del supervisor
     * @return Lista de empleados supervisados
     * @throws EntityNotFoundException Si no se encuentra el supervisor
     */
    List<Empleado> getEmpleadosSupervisados(Long idSupervisor) throws EntityNotFoundException;

    /**
     * Obtiene un empleado supervisado por un supervisor específico.
     *
     * @param idSupervisor El ID del supervisor.
     * @param idEmpleado   El ID del empleado supervisado.
     * @return El empleado supervisado por el supervisor especificado.
     * @throws EntityNotFoundException Si el supervisor o el empleado no se encuentran en la base de datos.
     */
    Empleado getEmpleadoSupervisado(Long idSupervisor, Long idEmpleado) throws EntityNotFoundException;
}
