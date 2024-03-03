package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.domain.Evento;
import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.domain.Seguro;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class EmpleadoDTO {
    private Long idEmpleado;
    private String nombres;
    private String apellidos;
    private String dni;
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;
    private String email;
    private List<Empleado> empleados = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private Rol rol;
    private Seguro seguro;
}
