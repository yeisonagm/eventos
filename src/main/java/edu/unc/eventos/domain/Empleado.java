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
     * Relación con Supervisor.
     */
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    @JsonIdentityReference(alwaysAsId = true)
    private Empleado supervisor;

    /**
     * Relación con Empleado.
     */
    @OneToMany(mappedBy = "supervisor")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Empleado> empleados_supervisados;

    /**
     * Relación con Evento.
     */
    @OneToMany(mappedBy = "empleado")
    @JsonIdentityReference(alwaysAsId = true)
    private List<Evento> eventos = new ArrayList<>();

    /**
     * Relación con Rol.
     */
    @ManyToOne
    @JoinColumn(name = "id_rol")
    @JsonIdentityReference(alwaysAsId = true)
    private Rol rol;

    /**
     * Relación con Seguro.
     */
    @OneToOne(mappedBy = "empleado")
    @JsonIdentityReference(alwaysAsId = true)
    private Seguro seguro;
}
