/**
 * @file: DecoracionDTO.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 04, 2024 03:51:12 PM
 * @description: Esta clase es un DTO (Data Transfer Object) que se utiliza para transferir datos de la clase de dominio 'Decoracion' entre diferentes capas de la aplicación, especialmente para operaciones de mapeo de datos.
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DecoracionDTO {
    /**
     * El campo 'idDecoracion' corresponde al identificador único de la decoración en el sistema.
     */
    private Long idDecoracion;

    /**
     * El campo 'descripcion' es una descripción detallada de la decoración.
     */
    private String descripcion;

    /**
     * El campo 'precio' representa el costo asociado a la decoración.
     */
    private Double precio;

    /**
     * El campo 'color' especifica el color predominante de la decoración.
     */
    private String color;

    /**
     * El campo 'eventos' es una lista de todos los eventos que están asociados a esta decoración.
     */
    private List<Evento> eventos = new ArrayList<>();
}
