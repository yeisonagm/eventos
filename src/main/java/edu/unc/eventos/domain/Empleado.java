package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    private String nombres;
    private String apellidos;
    private String dni;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;

    // Relación con Evento

    // Relación con Rol

    // Relación con Seguro

}
