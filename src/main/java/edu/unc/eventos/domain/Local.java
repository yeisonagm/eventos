package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;
    private String nombre;
    private String ubicacion;
    private Integer aforo;
    private String referencia;

    // Relación con Evento
    @OneToMany(mappedBy = "local")
    private List<Evento> eventos = new ArrayList<>();
}
