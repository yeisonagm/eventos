package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //relación con empleado
    @OneToMany
    @JoinColumn(name = "id_supervisor")
    private List<Empleado> empleados;

    // Relación con Evento
    @OneToMany(mappedBy = "empleado")
    private List<Evento> eventos = new ArrayList<>();

    // Relación con Rol
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    // Relación con Seguro
    @OneToOne(mappedBy = "empleado")
    private Seguro seguro;
}
