/**
 * @file: RolController.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 09:41:45 PM
 */
package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.exception.EntityNotFoundException;
import edu.unc.eventos.exception.IllegalOperationException;
import edu.unc.eventos.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImp implements RolService {
    @Autowired
    private RolRepository rolRepository;

    /**
     * Este método devuelve una lista de todos los roles
     *
     * @return Lista de entidades de tipo rol.
     */
    @Override
    @Transactional
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    /**
     * Este método devuelve un rol de acuerdo al ID
     *
     * @param idRol Identificador del Rol
     * @return Rol listado de acuerdo al ID
     * @throws EntityNotFoundException Si el Rol no existe
     */
    @Override
    @Transactional
    public Rol getRolById(Long idRol) throws EntityNotFoundException {
        Optional<Rol> rol = rolRepository.findById(idRol);
        if (rol.isEmpty()) {
            throw new EntityNotFoundException("El Rol con el ID proporcionado no se encontró.");
        }
        return rol.get();
    }

    /**
     * Guardar un nuevo rol
     *
     * @param 'Objeto' del tipo rol que se va a persistir
     * @return El objeto luego de persistirlo
     * @throws IllegalOperationException Si el nombre del departamento es inválido o ya existe en la persistencia
     */
    @Override
    @Transactional
    public Rol save(Rol rol) throws IllegalOperationException {
        Rol rolDB = rolRepository.findByNombre(rol.getNombre());
        if (rolDB != null) {
            throw new IllegalOperationException("El nombre del rol ya existe");
        }
        return rolRepository.save(rol);
    }

    /**
     * @param idRol    Identificador del Rol
     * @param 'Objeto' del tipo rol que se va a persistir
     * @return Se actuliza en la DB.
     * @throws EntityNotFoundException   Si el Rol no existe en la DB
     * @throws IllegalOperationException Si el nombre del departamento es inválido o ya existe en la persistencia
     */
    @Override
    @Transactional
    public Rol update(Long idRol, Rol rol) throws EntityNotFoundException, IllegalOperationException {
        Optional<Rol> rolEntity = rolRepository.findById(idRol);
        Rol rolDB = rolRepository.findByNombre(rol.getNombre());

        if (rolEntity.isEmpty())
            throw new EntityNotFoundException("El Rol con el ID proporcionado no se encontró.");
        if (rolDB != null && !rolDB.getIdRol().equals(idRol)) {
            throw new IllegalOperationException("El nombre del rol ya existe.");
        }
        rol.setIdRol(idRol);
        return rolRepository.save(rol);
    }

    /**
     * Elimina un rol existente
     *
     * @param idRol Id de la decoración que se quiere eliminar
     * @throws EntityNotFoundException   Si el rol no se encuentra en la base de datos.
     * @throws IllegalOperationException Si el rol tiene eventos asociados.
     */
    @Override
    public void delete(Long idRol) throws EntityNotFoundException, IllegalOperationException {

        Rol rol = rolRepository.findById(idRol).orElseThrow(
                () -> new EntityNotFoundException("El rol con el ID proporcionado no se encontró")
        );
        if (!rol.getEmpleados().isEmpty()) {
            throw new IllegalOperationException("El rol tiene empleados asociados.");
        }

        rolRepository.deleteById(idRol);
    }
    /**
     * Obtiene todos los empleados asociados a un rol específico.
     *
     * @param idRol ID del rol del que se desean obtener los empleados.
     * @return Lista de empleados asociados al rol.
     * @throws EntityNotFoundException Si el rol no se encuentra en la base de datos.
     */
    @Override
    @Transactional
    public List<Empleado> getEmpleadosByRol(Long idRol) throws EntityNotFoundException {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el ID proporcionado"));
        return rol.getEmpleados();
    }
    /**
     * Obtiene un empleado específico asociado a un rol por los IDs especificados.
     *
     * @param idRol      ID del rol.
     * @param idEmpleado ID del empleado asociado al rol.
     * @return Empleado encontrado.
     * @throws EntityNotFoundException Si no se encuentra el empleado asociado al rol.
     */
    @Override
    @Transactional
    public Empleado getEmpleadoByRolId(Long idRol, Long idEmpleado) throws EntityNotFoundException {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con el ID proporcionado"));

        if (rol.getEmpleados() == null || rol.getEmpleados().isEmpty()) {
            throw new EntityNotFoundException("No se encontraron empleados asociados al rol");
        }

        Optional<Empleado> empleadoOpt = rol.getEmpleados().stream()
                .filter(empleado -> empleado.getIdEmpleado().equals(idEmpleado))
                .findFirst();

        if (empleadoOpt.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el empleado asociado al rol con el ID proporcionado");
        }

        return empleadoOpt.get();
    }



}
