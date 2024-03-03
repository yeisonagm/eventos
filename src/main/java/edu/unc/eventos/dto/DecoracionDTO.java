package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DecoracionDTO {
    private Long idDecoracion;
    private String descripcion;
    private Double precio;
    private String color;
    private List<Evento> eventos = new ArrayList<>();
}

