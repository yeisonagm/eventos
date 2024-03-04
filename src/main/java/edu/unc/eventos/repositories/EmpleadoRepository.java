/**
 * @file: EmpleadoRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 5:40:00 PM
 * Repository para la entidad Empleado. Proporciona métodos de consulta personalizados para la clase Empleado.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    /**
     * Busca empleados por nombre.
     *
     * @param nombres Los nombres del empleado.
     * @return Lista de empleados con los nombres proporcionados.
     */
    List<Empleado> findByNombres(String nombres);

    /**
     * Busca empleados por apellido.
     *
     * @param apellidos Los apellidos del empleado.
     * @return Lista de empleados con los apellidos proporcionados.
     */
    List<Empleado> findByApellidos(String apellidos);

    /**
     * Busca empleados por rol.
     *
     * @param rolNombre El nombre del rol del empleado.
     * @return Lista de empleados con el rol proporcionado.
     */
    List<Empleado> findByRolNombre(String rolNombre);

    /**
     * Busca empleados por dirección.
     *
     * @param direccion La dirección del empleado.
     * @return Lista de empleados con la dirección proporcionada.
     */
    List<Empleado> findByDireccion(String direccion);

    /**
     * Busca un empleado por número de teléfono.
     *
     * @param telefono El número de teléfono del empleado.
     * @return El empleado con el número de teléfono proporcionado.
     */
    Empleado findByTelefono(String telefono);

    /**
     * Busca un empleado por email.
     *
     * @param email El email del empleado.
     * @return El empleado con el email proporcionado.
     */
    Empleado findByEmail(String email);
}
