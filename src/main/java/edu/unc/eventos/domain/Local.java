/**
 * @file: Local.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 00:30:00 AM
 * @description: Esta clase es parte del dominio del sistema y representa un local donde se pueden llevar a cabo eventos.
 * * Cada instancia de esta clase contiene información sobre el nombre, ubicación,
 * * aforo, referencia y una lista de eventos asociados al local.
 */

package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data

public class Local {
    /**
     * El campo 'idLocal' corresponde al identificador único del local en el sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /** Relación con Evento.
     * El campo eventos representa la lista de eventos asociados al local.
     * Un local puede tener múltiples eventos asociados.
     */
    @OneToMany(mappedBy = "local")
    private List<Evento> eventos = new ArrayList<>();
}
