package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    /** Código del seguro. */
    @NotBlank(message = "El código no puede estar en blanco")
    @Size(max = 11, message = "El código debe tener minimo 11 caracteres")
    @Pattern(regexp = "^[0-9]*$", message = "El código solo puede contener números.")
    private String codigo;

    /** Fecha de inscripción del seguro */
    @NotBlank(message = "La fecha de inscripción no puede estar vacía")
    @Past(message = "La fecha de inscripción debe ser en el pasado")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;

   /** Relación con Empleado */
    @OneToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
}
