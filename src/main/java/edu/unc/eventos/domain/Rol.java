package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    private String nombre;

    // Relación con Empleado
    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados;

}
