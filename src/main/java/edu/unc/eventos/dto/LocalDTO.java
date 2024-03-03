package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class LocalDTO {
    private Long idLocal;
    private Integer aforo;
    private String nombre;
    private String referencia;
    private String ubicacion;
    private List<Evento> eventos = new ArrayList<>();
}
