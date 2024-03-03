package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

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

    // Relación con Cliente

    // Relación con Plato

}
