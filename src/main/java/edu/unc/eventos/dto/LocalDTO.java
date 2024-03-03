/**
 * @file: LocalDTO.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 03, 2024 00:59:00 AM
 * @description: Esta clase es un DTO (Data Transfer Object) que se utiliza para transferir datos de la clase de dominio 'Local' entre diferentes capas de la aplicación, especialmente para operaciones de mapeo de datos.
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocalDTO {
    /**
     * El campo 'idLocal' corresponde al identificador único del local en el sistema.
     */
    private Long idLocal;

    /**
     * El campo 'aforo' corresponde a la capacidad máxima de personas permitidas en el local.
     */
    private Integer aforo;

    /**
     * El campo 'nombre' corresponde al nombre descriptivo del local.
     */
    private String nombre;

    /**
     * El campo 'referencia' es una descripción adicional del local.
     */
    private String referencia;

    /**
     * El campo 'ubicacion' corresponde a la dirección física del local.
     */
    private String ubicacion;

    /**
     * El campo 'eventos' es una lista de todos los eventos que están asociados a este local.
     */
    private List<Evento> eventos = new ArrayList<>();
}
