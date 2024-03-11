/**
 * @file: RolService.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 10:32:12 PM
 * @description: Interfaz que define los servicios disponibles para la gestión de roles en el sistema.
 * Proporciona métodos para la recuperación, creación, actualización y eliminación de roles.
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;

import java.util.List;

public interface RolService {
    /**
     * Recupera todos los roles registrados en el sistema.
     *
     * @return Lista de objetos Rol.
     */
    List<Rol> getAll();

    /**
     * Recupera un rol específico según su ID.
     *
     * @param idRol Identificador único del rol.
     * @return Objeto Rol correspondiente al ID proporcionado.
     * @throws EntityNotFoundException Si no se encuentra ningún rol con el identificador especificado.
     */
    Rol getRolById(Long idRol) throws EntityNotFoundException;

    /**
     * Guarda un nuevo rol en el sistema.
     *
     * @param rol Objeto Rol que se va a guardar.
     * @return Objeto Rol guardado.
     * @throws IllegalOperationException Si hay algún problema con los datos del rol que impide su guardado.
     */
    Rol save(Rol rol) throws IllegalOperationException;

    /**
     * Actualiza un rol existente en el sistema.
     *
     * @param idRol Identificador único del rol que se va a actualizar.
     * @param rol   Objeto Rol con los nuevos datos.
     * @return Objeto Rol actualizado.
     * @throws EntityNotFoundException   Si no se encuentra ningún rol con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema con los datos del rol que impide su actualización.
     */
    Rol update(Long idRol, Rol rol) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Elimina un rol del sistema según su identificador único.
     *
     * @param idRol Identificador único del rol que se va a eliminar.
     * @throws EntityNotFoundException   Si no se encuentra ningún rol con el identificador especificado.
     * @throws IllegalOperationException Si hay algún problema que impide la eliminación del rol.
     */
    void delete(Long idRol) throws EntityNotFoundException, IllegalOperationException;

    /**
     * Obtiene todos los empleados asociados a un rol específico.
     *
     * @param idRol ID del rol del que se desean obtener los empleados.
     * @return Lista de empleados asociados al rol.
     * @throws EntityNotFoundException Si el rol no se encuentra en la base de datos.
     */
    List<Empleado> getAllEmpleadosByIdRol(Long idRol) throws EntityNotFoundException;

    /**
     * Obtiene un empleado asociado a un rol específico por su ID.
     *
     * @param idRol      ID del rol al que está asociado el empleado.
     * @param idEmpleado ID del empleado que se desea obtener.
     * @return Objeto Empleado asociado al rol con el ID especificado.
     */


    Empleado getByIdEmpleadoByRolId(Long idRol, Long idEmpleado) throws EntityNotFoundException;
}
