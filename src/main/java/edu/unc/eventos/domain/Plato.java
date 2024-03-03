package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    /** Nombre del Plato. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 4, max = 25, message = "El nombre debe tener entre 4 a 25 caracteres.")
    private String nombre;

    /** Descripción del plato. */
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(min = 6, max = 255, message = "La descripción del plato debe tener entre 6 a 255 caracteres.")
    private String descripcion;

    /** Tipo del plato. */
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 15, message = "La descripción del plato debe tener entre 5 a 15 caracteres.")
    private String tipo;


    /**Relación con Evento. */
    @ManyToMany(mappedBy = "platos")
    private List<Evento> eventos = new ArrayList<>();
}
