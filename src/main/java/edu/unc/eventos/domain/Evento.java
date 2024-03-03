package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;
    private String nombre;
    private Integer numPresonas;
    @Temporal(TemporalType.DATE)
    private String fecha;
    private Integer duracion;
    private String total;

    // Relación con Empleado
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Relación con Plato
    @ManyToMany
    @JoinTable(
            name = "evento_plato",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_plato")
    )
    private List<Plato> platos = new ArrayList<>();


    // Relación con Decoracion
    @ManyToOne
    @JoinColumn(name = "id_decoracion")
    private Decoracion decoracion;

    // Relación con Local
    @ManyToOne
    @JoinColumn(name = "id_local")
    private Local local;
}
