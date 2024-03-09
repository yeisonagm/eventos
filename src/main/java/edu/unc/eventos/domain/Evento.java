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
    @Temporal(TemporalType.DATE)
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
     * Relación con Empleado: Representa el empleado asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    @JsonIdentityReference(alwaysAsId = true)
    private Empleado empleado;

    /**
     * Relación con Cliente: Representa el cliente asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @JsonIdentityReference(alwaysAsId = true)
    private Cliente cliente;

    /**
     * Relación con Plato: Representa los platos asociados al evento.
     */
    @ManyToMany
    @JoinTable(
            name = "evento_plato",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_plato")
    )
    @JsonIdentityReference(alwaysAsId = true)
    private List<Plato> platos = new ArrayList<>();


    /**
     * Relación con Decoración: Representa la decoración asociada al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_decoracion")
    @JsonIdentityReference(alwaysAsId = true)
    private Decoracion decoracion;

    /**
     * Relación con Local: Representa el local asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_local")
    @JsonIdentityReference(alwaysAsId = true)
    private Local local;
}
