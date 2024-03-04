/**
 * @file: Evento.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 00:39:54 AM
 */
package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Evento {
    /**
     * El campo idEvento es el identificador único de cada Evento en la base de datos.
     * Este campo es generado automáticamente por la base de datos cuando se crea un nuevo Evento.
     * Cada evento tiene un identificador único, un nombre, capacidad de asistentes, fecha, duración, precio total,
     * y puede involucrar a empleados, clientes, platos, decoraciones y un local específico.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    /**
     * El campo Nombre es una descripción textual del evento.
     * Este campo es obligatorio y su longitud debe estar entre 4 y 50 caracteres.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 4, max = 50, message = "El nombre debe tener entre 4 a 50 caracteres.")
    private String nombre;

    /**
     * El campo numPersonas indica el número de personas que se espera asistan al evento.
     * Debe ser mayor que 0 y menor que 500.
     */
    @NotBlank(message = "El número de personas no puede estar vacío.")
    @Min(value = 1, message = "El número de personas debe ser mayor que 0.")
    @Max(value = 499, message = "El número de personas debe ser menor que 500.")
    private Integer numPersonas;

    /**
     * El campo fecha representa la fecha en la que se llevará a cabo el evento.
     */
    @Temporal(TemporalType.DATE)
    @Future(message = "La fecha debe ser futura")
    private Date fecha;

    /**
     * El campo duración indica la duración estimada del evento en horas.
     * Debe ser mayor a 1 hora y menor que 10 horas.
     */
    @NotBlank(message = "La duración no puede estar vacío.")
    @Min(value = 1, message = "La duración debe ser mayor a 1 hora.")
    @Max(value = 10, message = "La duración debe ser menor que 10 horas.")
    private Integer duracion;

    /**
     * El campo total representa el precio total del evento.
     * Debe estar entre 3000.00 y 20000.00, con un máximo de 5 dígitos y 2 decimales.
     */
    @NotBlank(message = "El precio no puede estar vacío")
    @DecimalMin(value = "3000.00", message = "El precio mínimo debe ser mayor que 3,000")
    @DecimalMax(value = "20000.00", message = "El precio máximo debe ser menor que 20,000")
    @Digits(integer = 5, fraction = 2, message = "El precio debe tener un máximo de 5 dígitos, con 2 decimales")
    private Double total;

    /**
     * Relación con Empleado: Representa el empleado asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    /**
     * Relación con Cliente: Representa el cliente asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**
     * Relación con Plato: Representa los platos asociados al evento.
     */
    @ManyToMany
    @JoinTable(
            name = "evento_plato",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_plato")
    )
    private List<Plato> platos = new ArrayList<>();


    /**
     * Relación con Decoración: Representa la decoración asociada al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_decoracion")
    private Decoracion decoracion;

    /**
     * Relación con Local: Representa el local asociado al evento.
     */
    @ManyToOne
    @JoinColumn(name = "id_local")
    private Local local;
}
