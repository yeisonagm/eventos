/**
 * @file: Local.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 00:48:00 AM
 * @description: Esta clase es parte del dominio del sistema y representa un plato que puede ser servido en un evento.
 *  * Cada instancia de esta clase contiene información sobre el nombre, descripción,
 *  * tipo y una lista de eventos en los que se puede servir el plato.
 */

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

    /** Nombre del Plato.
     * El campo Nombre es una descripción textual del plato.
     * Este campo es obligatorio y debe tener entre 4 y 25 caracteres.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 4, max = 25, message = "El nombre debe tener entre 4 a 25 caracteres.")
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
    @NotEmpty(message = "La descripción no puede estar vacía")
    @Size(min = 5, max = 15, message = "La descripción del plato debe tener entre 5 a 15 caracteres.")
    private String tipo;


    /** Relación con Evento.
     * Representa la lista de eventos en los que se puede servir el plato.
     */
    @ManyToMany(mappedBy = "platos")
    private List<Evento> eventos = new ArrayList<>();
}
