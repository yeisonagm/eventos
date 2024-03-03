package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;
    private String codigo;
    private String fechaIncripcion;

    // Relación con Empleado
    @OneToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
}
