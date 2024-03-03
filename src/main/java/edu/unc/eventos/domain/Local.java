package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;

    /** Nombre del Local. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 6, max = 50, message = "El nombre debe tener entre 6 a 50 caracteres.")
    private String nombre;

    /** Ubicaión del Local. */
    @NotBlank(message = "La Ubicación no puede estar vacío.")
    @Size(min = 10, max = 50, message = "La ubicación debe tener entre 10 a 50 caracteres.")
    private String ubicacion;

    /** Aforo del local. */
    @NotBlank(message = "El aforo no puede estar vacío.")
    @Min(value = 10, message = "El aforo debe ser mayor a 10.")
    @Max(value = 500, message = "El duración debe ser menor que 500.")
    private Integer aforo;

    /** Referencia del Local. */
    @Size(min = 5, max = 30, message = "La referencia debe tener entre 10 a 30 caracteres.")
    private String referencia;

    /**Relación con Evento. */
    @OneToMany(mappedBy = "local")
    private List<Evento> eventos = new ArrayList<>();
}
