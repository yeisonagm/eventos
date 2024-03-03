package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Empleado;
import lombok.Data;

@Data
public class SeguroDTO {
    private Long idSeguro;
    private String codigo;
    private String fechaIncripcion;
    private Empleado empleado;
}
