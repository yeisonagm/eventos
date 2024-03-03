package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    /** Nombre del Rol. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 2, max = 15, message = "El nombre debe tener entre 4 a 15 caracteres.")
    private String nombre;

    /** Relación con Empleado. */
    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados = new ArrayList<>();
}
