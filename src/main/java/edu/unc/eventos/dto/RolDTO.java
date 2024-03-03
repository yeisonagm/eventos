/**
 * @file: RolDTO.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 05:22:48 PM
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Empleado;
import lombok.Data;

import java.util.List;

/**
 * La clase RolDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con roles en el sistema. A diferencia de la clase Rol, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */
@Data
public class RolDTO {
    /**
     * El campo 'idRol' es el identificador único del rol.
     */
    private Long idRol;

    /**
     * El campo 'nombre' representa el nombre del rol.
     */
    private String nombre;

    /**
     * El campo 'empleados' es una lista de los empleados asociados a este rol.
     */
    private List<Empleado> empleados;
}
