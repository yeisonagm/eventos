package edu.unc.eventos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

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

}
