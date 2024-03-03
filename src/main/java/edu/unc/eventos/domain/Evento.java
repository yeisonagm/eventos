package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    /** Nombre del evento. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 4, max = 50, message = "El nombre debe tener entre 4 a 50 caracteres.")
    private String nombre;

    /** Npumero de personas del evento. */
    @NotBlank(message = "El número de personas no puede estar vacío.")
    @Min(value = 1, message = "El número de personas debe ser mayor que 0.")
    @Max(value = 499, message = "El número de personas debe ser menor que 500.")
    private Integer numPersonas;

    /** Fecha del evento. */
    @Temporal(TemporalType.DATE)
    private String fecha;

    /** Duración del evento. */
    @NotBlank(message = "La duración no puede estar vacío.")
    @Min(value = 1, message = "La duración debe ser mayor a 1 hora.")
    @Max(value = 10, message = "El duración debe ser menor que 10 horas.")
    private Integer duracion;

    /** Precio del evento. */
    @NotBlank(message = "El precio no puede estar vacío")
    @DecimalMin(value = "3000.00", message = "El precio mínimo debe ser mayor que 3 000")
    @DecimalMax(value = "20000.00", message = "El precio máximo debe ser menor que 20 000")
    @Digits(integer = 5, fraction = 2, message = "El precio debe tener un máximo de 5 dígitos, con 2 decimales")
    private Double total;

    // Relación con Empleado
    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    /**Relación con Plato. */
    @ManyToMany
    @JoinTable(
            name = "evento_plato",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_plato")
    )
    private List<Plato> platos = new ArrayList<>();


    /**Relación con Decoración. */
    @ManyToOne
    @JoinColumn(name = "id_decoracion")
    private Decoracion decoracion;

    /**Relación con Local. */
    @ManyToOne
    @JoinColumn(name = "id_local")
    private Local local;
}
