/**
 * @file: EmpleadoDTO.java
 * @author: (c)2024 Yeison García
 * @created: Mar 05, 2024 4:14:18 AM
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idDecoracion")
public class Decoracion {
    /**
     * El campo 'idDecoracion' corresponde al identificador único de la decoración en el sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Relación con Evento.
     */
    @OneToMany(mappedBy = "decoracion", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Evento> eventos = new ArrayList<>();
}
