/**
 * @file: Rol.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 00:10:00 AM
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * La clase Rol es una entidad que representa el rol que un empleado puede tener en el sistema.
 * Un rol es una forma de categorizar a los empleados según sus responsabilidades y privilegios en el sistema.
 * Cada instancia de Rol tiene un identificador único, un nombre y una lista de empleados asociados.
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRol")
public class Rol {
    /**
     * El campo 'idRol' es el identificador único del rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    /**
     * El campo 'nombre' representa el nombre del rol.
     */
    private String nombre;

    /**
     * El campo 'empleados' es una lista de los empleados asociados a este rol.
     */
    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados;
}