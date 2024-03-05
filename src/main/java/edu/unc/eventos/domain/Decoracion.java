/**
 * @file: EmpleadoDTO.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 4:14:18 AM
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDecoracion")
public class Decoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDecoracion;

    /**
     * Descripción de la decoración.
     */
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(min = 6, max = 255, message = "La descripción de la decoración debe tener entre 6 a 255 caracteres.")
    private String descripcion;

    /**
     * Precio de la decoración.
     */
    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "50.00", message = "El precio mínimo debe ser mayor que 50 soles")
    @DecimalMax(value = "10000.00", message = "El precio máximo debe ser menor que 10 000 soles")
    @Digits(integer = 5, fraction = 2, message = "El precio debe tener un máximo de 5 dígitos, con 2 decimales")
    private BigDecimal precio;

    /**
     * Color predominante de la decoración.
     */
    @Size(min = 3, max = 20, message = "El color de la decoración debe tener entre 3 a 20 caracteres.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚ\\s]*$", message = "El color solo puede contener letras")
    private String color;

    /**
     * Relación con Evento.
     */
    @OneToMany(mappedBy = "decoracion", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();
}
