/**
 * @file: Cliente.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 17:28:00 PM
 * Representa a un cliente en el contexto de un dominio específico.
 * Esta clase está anotada para persistencia y validación mediante anotaciones JPA.
 */
package edu.unc.eventos.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cliente extends RepresentationModel<Cliente> {
    /**
     * El campo 'idCliente' es el identificador único del cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Lista de eventos asociados al cliente.
     * Esto representa una relación uno a muchos con la clase Evento.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Evento> eventos = new ArrayList<>();
}
