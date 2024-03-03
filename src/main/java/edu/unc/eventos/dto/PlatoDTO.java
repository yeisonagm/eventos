/**
 * @file: PlatoDTO.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 03, 2024 01:05:00 AM
 * @description: Esta clase es un DTO (Data Transfer Object) que se utiliza para transferir datos de la clase de dominio 'Plato' entre diferentes capas de la aplicación, especialmente para operaciones de mapeo de datos.
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlatoDTO {
    /**
     * El campo 'idPlato' corresponde al identificador único del plato en el sistema.
     */
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
    private List<Evento> eventos = new ArrayList<>();
}
