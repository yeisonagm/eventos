package edu.unc.eventos.domain;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEmpleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    /**
     * Nombre(s) del Empleado.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El nombre solo puede contener letras")
    private String nombres;

    /**
     * Apellidos del Empleado.
     */
    @NotBlank(message = "Debe al menos colocar un apellido")
    @Size(min = 6, max = 30, message = "El Apellido debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El apellido solo puede contener letras")
    private String apellidos;

    /**
     * Documento de identidad del empleado.
     */
    @Column(unique = true)
    @NotBlank(message = "El Documento de identidad no puede estar vacío.")
    @Size(min = 8, max = 8, message = "El Documento de identidad debe tener entre 8 a 8 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El Documento de identidad solo puede contener números.")
    private String dni;

    /**
     * Fecha de nacimiento del empleado.
     */
    @Temporal(TemporalType.DATE)
    @PastOrPresent(message = "Debe tener al menos 18 años")
    @Past(message = "La fecha de inscripción debe ser en el pasado")
    private Date fechaNacimiento;

    /**
     * Dirección del empleado.
     */
    @NotBlank(message = "La Dirección no puede estar vacío.")
    @Size(min = 10, max = 50, message = "La Dirección debe tener entre 10 a 50 caracteres.")
    private String direccion;

    /**
     * Número de teléfono del empleado.
     */
    @Column(unique = true)
    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El número de teléfono solo puede contener números.")
    private String telefono;

    /**
     * Email del empleado.
     */
    @Column(unique = true)
    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del correo electrónico no es válido")
    @Size(max = 30, message = "El email debe tener menos de 30 caracteres.")
    private String email;

    /**
     * Relación con Supervisor.
     */
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    @JsonIgnore
    private Empleado supervisor;

    /**
     * Relación con Empleado.
     */
    @OneToMany(mappedBy = "supervisor")
    @JsonIgnore
    private List<Empleado> empleados_supervisados;

    /**
     * Relación con Evento.
     */
    @OneToMany(mappedBy = "empleado")
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();

    /**
     * Relación con Rol.
     */
    @ManyToOne
    @JoinColumn(name = "id_rol")
    @JsonIgnore
    private Rol rol;

    /**
     * Relación con Seguro.
     */
    @OneToOne(mappedBy = "empleado")
    @JsonIgnore
    private Seguro seguro;
}
