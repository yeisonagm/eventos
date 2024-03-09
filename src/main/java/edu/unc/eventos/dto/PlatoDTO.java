/**
 * @file: PlatoDTO.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 03, 2024 01:05:00 AM
 * @description: Esta clase es un DTO (Data Transfer Object) que se utiliza para transferir datos de la clase de dominio 'Plato' entre diferentes capas de la aplicación, especialmente para operaciones de mapeo de datos.
 */
package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPlato")
public class PlatoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    /** Nombre del Plato.
     * El campo Nombre es una descripción textual del plato.
     * Este campo es obligatorio y debe tener entre 4 y 25 caracteres.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 4, max = 25, message = "El nombre debe tener entre 4 a 25 caracteres.")
    @Column(unique = true)
    private String nombre;

    /** Descripción del plato.
     * Representa una descripción detallada del plato.
     * Este campo es obligatorio y debe tener entre 6 y 255 caracteres.
     */
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(min = 6, max = 255, message = "La descripción del plato debe tener entre 6 a 255 caracteres.")
    private String descripcion;

    /** Tipo del plato.
     * El campo tipo es una descripción textual del plato, representa el tipo o categoría del plato.
     * Este campo es obligatorio y debe tener entre 5 y 15 caracteres.
     */
    @NotEmpty(message = "El tipo no puede estar vacío")
    @Size(min = 5, max = 15, message = "La tipo del plato debe tener entre 5 a 15 caracteres.")
    private String tipo;

    /**
     * El campo 'eventos' es una lista de todos los eventos en los que se puede servir este plato.
     */
    @JsonIdentityReference(alwaysAsId = true)
    private List<EventoDTO> eventos = new ArrayList<>();
}
