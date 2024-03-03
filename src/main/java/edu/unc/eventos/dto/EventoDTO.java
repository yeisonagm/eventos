/**
 * @file: EventoDTO.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 05:09:54 PM
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase EventoDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con eventos en el sistema. A diferencia de la clase Evento, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */
@Data
public class EventoDTO {
    /**
     * El campo 'idEvento' es el identificador único del evento.
     */
    private Long idEvento;

    /**
     * El campo 'nombre' representa el nombre del evento.
     */
    private String nombre;

    /**
     * El campo 'numPersonas' indica el número de personas esperadas para el evento.
     */
    private Integer numPersonas;

    /**
     * El campo 'fecha' representa la fecha en la que se llevará a cabo el evento.
     */
    private String fecha;

    /**
     * El campo 'duracion' indica la duración estimada del evento en horas.
     */
    private Integer duracion;

    /**
     * El campo 'total' representa el precio total del evento.
     */
    private String total;

    /**
     * El campo 'empleado' representa el empleado asociado al evento.
     */
    private Empleado empleado;

    /**
     * El campo 'cliente' representa el cliente asociado al evento.
     */
    private Cliente cliente;

    /**
     * El campo 'platos' es una lista de los platos asociados al evento.
     */
    private List<Plato> platos = new ArrayList<>();

    /**
     * El campo 'decoracion' representa la decoración asociada al evento.
     */
    private Decoracion decoracion;

    /**
     * El campo 'local' representa el local donde se llevará a cabo el evento.
     */
    private Local local;
}
