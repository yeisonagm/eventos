package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClienteDTO {
    private Long idCliente;
    private String di;
    private String nombre;
    private String direccion;
    private String telefono;
    private List<Evento> eventos = new ArrayList<>();
}
