/**
 * @file: ClienteDTO.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 18:50:00 PM
 * Data Transfer Object (DTO) para la entidad Cliente. Contiene información específica
 * para la transferencia de datos relacionados con clientes entre diferentes capas de la aplicación.
 * Utilizado para evitar la exposición innecesaria de detalles internos de la entidad Cliente.
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClienteDTO {

    // Identificador único del cliente
    private Long idCliente;

    // Número de documento de identidad del cliente
    private String di;

    // Nombre del cliente
    private String nombre;

    // Dirección del cliente
    private String direccion;

    // Número de teléfono del cliente
    private String telefono;

    // Lista de eventos asociados al cliente
    private List<Evento> eventos = new ArrayList<>();
}
