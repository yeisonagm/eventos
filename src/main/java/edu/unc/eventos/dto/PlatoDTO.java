package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlatoDTO {
    private Long idPlato;
    private String nombre;
    private String descripcion;
    private String tipo;
    private List<Evento> eventos = new ArrayList<>();
}
