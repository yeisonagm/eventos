package edu.unc.eventos.dto;

import edu.unc.eventos.domain.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventoDTO {
    private Long idEvento;
    private String nombre;
    private Integer numPersonas;
    private String fecha;
    private Integer duracion;
    private String total;
    private Empleado empleado;
    private Cliente cliente;
    private List<Plato> platos = new ArrayList<>();
    private Decoracion decoracion;
    private Local local;
}
