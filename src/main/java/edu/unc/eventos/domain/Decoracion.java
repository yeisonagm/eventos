package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Decoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDecoracion;

    /** Descripción de la decoración. */
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(min = 6, max = 255, message = "La descripción de la decoración debe tener entre 6 a 255 caracteres.")
    private String descripcion;

    /** Precio de la decoración. */
    @NotBlank(message = "El precio no puede estar vacío")
    @DecimalMin(value = "50.00", message = "El precio mínimo debe ser mayor que 50")
    @DecimalMax(value = "10000.00", message = "El precio máximo debe ser menor que 10 000")
    @Digits(integer = 5, fraction = 2, message = "El precio debe tener un máximo de 5 dígitos, con 2 decimales")
    private Double precio;

    /** Color predominante de la decoración. */
    @Size(min = 3, max = 20, message = "El color de la decoración debe tener entre 3 a 20 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El color solo puede contener letras")
    private String color;

    /**Relación con Evento. */
    @OneToMany(mappedBy = "decoracion", cascade = CascadeType.ALL)
    private List<Evento> eventos = new ArrayList<>();
}
