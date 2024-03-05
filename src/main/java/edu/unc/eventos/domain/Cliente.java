/**
 * @file: Cliente.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 17:28:00 PM
 * Representa a un cliente en el contexto de un dominio específico.
 * Esta clase está anotada para persistencia y validación mediante anotaciones JPA.
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCliente")
public class Cliente {
    /**
     * El campo idCliente es el identificador único de cada cliente en la base de datos.
     * Este campo es generado automáticamente por la base de datos cuando se crea un nuevo cliente.
     * Cada cliente tiene un identificador único, un documento de identidad, nombre, dirección, número de teléfono,
     * y puede estar asociado con múltiples eventos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /**
     * Documento de identidad del cliente.
     * Debe ser único, no puede estar vacío, y debe tener una longitud entre 8 y 12 caracteres.
     * Solo puede contener números.
     */
    @Column(unique = true)
    @NotBlank(message = "El Documento de Identidad no puede estar vacío.")
    @Size(min = 8, max = 12, message = "El Documento de Identidad debe tener entre 8 y 12 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El Documento de Identidad solo puede contener números.")
    private String di;

    /**
     * Nombre del cliente.
     * No puede estar vacío, debe tener una longitud entre 6 y 50 caracteres, y solo puede contener letras.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 6, max = 50, message = "El nombre debe tener entre 6 y 50 caracteres.")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El nombre solo puede contener letras.")
    private String nombre;

    /**
     * Dirección del cliente.
     * No puede estar vacía y debe tener una longitud entre 10 y 50 caracteres.
     */
    @NotBlank(message = "La Dirección no puede estar vacía.")
    @Size(min = 10, max = 50, message = "La Dirección debe tener entre 10 y 50 caracteres.")
    private String direccion;

    /**
     * Número de teléfono del cliente.
     * No puede estar vacío, debe tener una longitud de 9 caracteres y solo puede contener números.
     */
    @NotBlank(message = "El número de celular no puede estar vacío.")
    @Size(min = 9, max = 9, message = "El número de celular debe tener 9 caracteres.")
    @Pattern(regexp = "^9[0-9]*$", message = "El número celular debe ser uno valido de Perú")
    private String telefono;

    /**
     * Lista de eventos asociados al cliente.
     * Esto representa una relación uno a muchos con la clase Evento.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Evento> eventos = new ArrayList<>();
}
