/**
 * @file: EmpleadoDTO.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 4:10:18 AM
 */
package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La clase EmpleadoDTO es un objeto de transferencia de datos (Data Transfer Object, DTO)
 * que se utiliza para encapsular los datos del empleado y enviarlos de la capa de dominio
 * a la capa de presentación.
 */
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEmpleado")
public class EmpleadoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;

    /**
     * Nombre(s) del Empleado.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚ\\s]*$", message = "El nombre solo puede contener letras")
    private String nombres;

    /**
     * Apellidos del Empleado.
     */
    @NotBlank(message = "Debe al menos colocar un apellido")
    @Size(min = 6, max = 30, message = "El Apellido debe tener entre 6 a 30 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚ\\s]*$", message = "El apellido solo puede contener letras")
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
    @JoinColumn(name = "id_supervisor")
    @JsonIdentityReference(alwaysAsId = true)
    private EmpleadoDTO supervisor;

    /**
     * Relación con Empleado.
     */
    @JsonIdentityReference(alwaysAsId = true)
    private List<EmpleadoDTO> empleados_supervisados;

    /**
     * Relación con Evento.
     */
    @JsonIdentityReference(alwaysAsId = true)
    private List<EventoDTO> eventos = new ArrayList<>();

    /**
     * Relación con Seguro.
     */
    private SeguroDTO seguro;

}
