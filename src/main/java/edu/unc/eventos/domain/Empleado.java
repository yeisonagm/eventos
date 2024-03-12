/**
 * @file: EmpleadoDTO.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 4:12:18 AM
 */
package edu.unc.eventos.domain;

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
     * Relación con Supervisor.
     */
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private Empleado supervisor;

    /**
     * Relación con Empleado.
     */
    @OneToMany(mappedBy = "supervisor")
    private List<Empleado> empleados_supervisados;

    /**
     * Relación con Evento.
     */
    @OneToMany(mappedBy = "empleado")
    private List<Evento> eventos = new ArrayList<>();

    /**
     * Relación con Seguro.
     */
    @OneToOne(mappedBy = "empleado")
    private Seguro seguro;
}
