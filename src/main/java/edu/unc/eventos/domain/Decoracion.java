package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Decoracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDecoracion;
    private String descripcion;
    private Double precio;
    private String color;

    // Relación con Evento
    @OneToMany(mappedBy = "decoracion", cascade = CascadeType.ALL)
    private List<Evento> eventos;
}
