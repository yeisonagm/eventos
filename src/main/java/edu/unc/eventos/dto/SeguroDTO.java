/**
 * @file: SeguroDTO.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 6:50:00 PM
 */
package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.unc.eventos.domain.Empleado;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

/**
 * La clase SeguroDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con seguros en el sistema. A diferencia de la clase Seguro, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */

@Data
public class SeguroDTO {
    /**
     * El campo idSeguro es el identificador único de cada seguro en la base de datos.
     * Este campo es generado automáticamente por la base de datos cuando se crea un nuevo seguro.
     * Cada seguro tiene un identificador único, un código, fecha de inscripción, y está asociado a un empleado.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    /**
     * Código del seguro.
     * No puede estar en blanco, debe tener un máximo de 11 caracteres y solo puede contener números.
     */
    @NotBlank(message = "El código no puede estar en blanco.")
    @Size(min = 11, max = 11, message = "El código debe tener un máximo de 11 caracteres.")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}-[0-9]{2}", message = "El código debe tener numero separados por guiones 4444-444-44")
    private String codigo;

    /**
     * Fecha de inscripción del seguro.
     * No puede estar en blanco y debe ser una fecha en el pasado.
     */
    @NotNull(message = "La fecha de inscripción no puede estar vacía.")
    @Past(message = "La fecha de inscripción debe ser en el pasado.")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;

    /**
     * Relación con Empleado.
     * Representa una relación uno a uno con la clase Empleado.
     */
    @OneToOne
    @JoinColumn(name = "id_empleado")
    @JsonIgnore
    private Empleado empleado;
}
