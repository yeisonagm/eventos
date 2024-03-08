/**
 * @file: EmpleadoDTO.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 4:12:18 AM
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase Empleado.
 */
@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEmpleado")
public class Empleado {
    /**
     * El campo 'idEmpleado' es el identificador único del empleado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    /**
     * El campo 'nombres' representa los nombres del empleado.
     */
    private String nombres;
    /**
     * El campo 'apellidos' representa los apellidos del empleado.
     */
    private String apellidos;
    /**
     * El campo 'dni' representa el documento de identidad del empleado.
     */
    private String dni;
    /**
     * El campo 'fechaNacimiento' representa la fecha de nacimiento del empleado.
     */
    private Date fechaNacimiento;
    /**
     * El campo 'direccion' representa la dirección del empleado.
     */
    private String direccion;
    /**
     * El campo 'telefono' representa el número de teléfono del empleado.
     */
    private String telefono;
    /**
     * El campo 'email' representa el correo electrónico del empleado.
     */
    private String email;
    /**
     * El campo 'empleados' es una lista de empleados asociados al empleado.
     */
    @OneToMany(mappedBy = "supervisor")
    private List<Empleado> empleados = new ArrayList<>();
    /**
     * El campo 'eventos' es una lista de eventos asociados al empleado.
     */
    @OneToMany(mappedBy = "empleado")
    private List<Evento> eventos = new ArrayList<>();
    /**
     * El campo 'rol' representa el rol del empleado.
     */
    @ManyToOne
    private Rol rol;
    /**
     * El campo 'seguro' representa el seguro del empleado.
     */
    @OneToOne(mappedBy = "empleado")
    private Seguro seguro;
}
