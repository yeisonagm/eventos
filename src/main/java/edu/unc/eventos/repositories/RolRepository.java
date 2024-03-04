/**
 * @file: RolRepository.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 6:00:00 PM
 * Repository para la entidad Rol. Proporciona métodos de consulta personalizados para la clase Rol.
 */
package edu.unc.eventos.repositories;

import edu.unc.eventos.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por nombre.
     *
     * @param nombre El nombre del rol.
     * @return El rol con el nombre proporcionado.
     */
    Rol findByNombre(String nombre);
}
