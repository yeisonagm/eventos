package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlato;
    private String nombre;
    private String descripcion;
    private String tipo;

    // Relación con Evento
    @ManyToMany(mappedBy = "platos")
    private List<Evento> eventos = new ArrayList<>();

}
