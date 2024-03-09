/**
 * @file: Rol.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 00:10:00 AM
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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
     * El campo Empleados es una lista de todos los empleados que están asociados a este Rol.
     * Esta lista puede estar vacía, lo que significa que actualmente no hay empleados que tengan este Rol.
     * Cuando un empleado es asignado a este Rol, se añade a esta lista.
     */
    @OneToMany(mappedBy = "rol")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Empleado> empleados = new ArrayList<>();
}