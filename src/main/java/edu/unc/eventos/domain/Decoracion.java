package edu.unc.eventos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

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

}
