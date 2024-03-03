package edu.unc.eventos.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    /** Nombre(s) del Empleado. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 6, max = 30, message = "El nombre debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El nombre solo puede contener letras")
    private String nombres;

    /** Apellidos del Empleado. */
    @NotBlank(message = "Debe al menos colocar un apellido")
    @Size(min = 6, max = 30, message = "El Apellido debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El apellido solo puede contener letras")
    private String apellidos;

    /** Documento de identidad del empleado. */
    @Column(unique = true)
    @NotBlank(message = "El Documento de identidad no puede estar vacío.")
    @Size(min = 8, max = 12, message = "El Documento de identidad debe tener entre 8 a 12 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El Documento de identidad solo puede contener números.")
    private String dni;

    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Debe tener al menos 18 años")
    private Date fechaNacimiento;

    /** Dirección del empleado. */
    @NotBlank(message = "La Dirección no puede estar vacío.")
    @Size(min = 10, max = 50, message = "La Dirección debe tener entre 10 a 50 caracteres.")
    private String direccion;

    /** Número de teléfono del empleado. */
    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El número de teléfono solo puede contener números.")
    private String telefono;

    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 30, message = "El email debe tener menos de 30 caracteres.")
    private String email;

    /**Relación con Empleado. */
    @OneToMany
    @JoinColumn(name = "id_supervisor")
    private List<Empleado> empleados;

    /**Relación con Evento. */
    @OneToMany(mappedBy = "empleado")
    private List<Evento> eventos = new ArrayList<>();

    /**Relación con Rol. */
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;

    /**Relación con Seguro. */
    @OneToOne(mappedBy = "empleado")
    private Seguro seguro;
}
