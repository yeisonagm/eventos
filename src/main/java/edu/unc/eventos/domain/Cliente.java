package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(unique = true)
    private String di;
    private String nombre;
    private String direccion;
    private String telefono;

    // Relación con Evento
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Evento> eventos;
}
