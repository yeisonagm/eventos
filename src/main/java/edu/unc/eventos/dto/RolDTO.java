package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Empleado;
import lombok.Data;

import java.util.List;

@Data
public class RolDTO {
    private Long idRol;
    private String nombre;
    private List<Empleado> empleados;
}
