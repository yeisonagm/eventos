/**
 * @file: Evento.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 00:39:54 AM
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idEvento")
public class Evento {
    /**
     * El campo 'idEvento' es el identificador único del evento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    /**
     * El campo 'nombre' representa el nombre del evento.
     */
    private String nombre;

    /**
     * El campo 'numPersonas' indica el número de personas esperadas para el evento.
     */
    private Integer numPersonas;

    /**
     * El campo 'fecha' representa la fecha en la que se llevará a cabo el evento.
     */
    private Date fecha;

    /**
     * El campo 'duracion' indica la duración estimada del evento en horas.
     */
    private Integer duracion;

    /**
     * El campo 'total' representa el precio total del evento.
     */
    private String total;

    /**
     * El campo 'empleado' representa el empleado asociado al evento.
     */
    @ManyToOne
    private Empleado empleado;

    /**
     * El campo 'cliente' representa el cliente asociado al evento.
     */
    @ManyToOne
    private Cliente cliente;

    /**
     * El campo 'platos' es una lista de los platos asociados al evento.
     */
    @ManyToMany
    private List<Plato> platos = new ArrayList<>();

    /**
     * El campo 'decoracion' representa la decoración asociada al evento.
     */
    @ManyToOne
    private Decoracion decoracion;

    /**
     * El campo 'local' representa el local donde se llevará a cabo el evento.
     */
    @ManyToOne
    private Local local;
}
