/**
 * @file: ClienteDTO.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 6:50:00 PM
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Evento;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * La clase ClienteDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con clientes en el sistema. A diferencia de la clase Cliente, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */

@Data
public class ClienteDTO {
    /**
     * El campo 'idCliente' es el identificador único del cliente.
     */
    private Long idCliente;

    /**
     * El campo 'di' representa el documento de identidad del cliente.
     */
    private String di;

    /**
     * El campo 'nombre' representa el nombre del cliente.
     */
    private String nombre;

    /**
     * El campo 'direccion' representa la dirección del cliente.
     */
    private String direccion;

    /**
     * El campo 'telefono' representa el número de teléfono del cliente.
     */
    private String telefono;

    /**
     * El campo 'eventos' es una lista de eventos asociados al cliente.
     */
    private List<Evento> eventos = new ArrayList<>();
}
