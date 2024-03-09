/**
 * @file: Plato.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 00:48:00 AM
 * @description: Esta clase es parte del dominio del sistema y representa un plato que puede ser servido en un evento.
 * * Cada instancia de esta clase contiene información sobre el nombre, descripción,
 * * tipo y una lista de eventos en los que se puede servir el plato.
 */

package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Plato {
    /**
     * El campo 'idPlato' corresponde al identificador único del plato en el sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;

    /**
     * El campo 'nombre' corresponde al nombre descriptivo del plato.
     */
    private String nombre;

    /**
     * El campo 'descripcion' es una descripción detallada del plato.
     */
    private String descripcion;

    /**
     * El campo 'tipo' representa el tipo o categoría del plato.
     */
    private String tipo;

    /**
     * El campo 'eventos' es una lista de todos los eventos en los que se puede servir este plato.
     */
    @ManyToMany(mappedBy = "platos")
    private List<Evento> eventos = new ArrayList<>();
}
