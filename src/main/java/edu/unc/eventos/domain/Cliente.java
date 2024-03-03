package edu.unc.eventos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /** Documento de identidad del cliente. */
    @Column(unique = true)
    @NotBlank(message = "El Documento de identidad no puede estar vacío.")
    @Size(min = 8, max = 12, message = "El Documento de identidad debe tener entre 8 a 12 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El Documento de identidad solo puede contener números.")
    private String di;

    /** Nombre del cliente. */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 6, max = 50, message = "El nombre debe tener entre 6 a 50 caracteres.")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "El nombre solo puede contener letras")
    private String nombre;

    /** Dirección del cliente. */
    @NotBlank(message = "La Dirección no puede estar vacío.")
    @Size(min = 10, max = 50, message = "La Dirección debe tener entre 10 a 50 caracteres.")
    private String direccion;

    /** Número de teléfono del cliente. */
    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Size(min = 9, max = 9, message = "El número de teléfono debe tener 9 caracteres.")
    @Pattern(regexp = "^[0-9]*$", message = "El número de teléfono solo puede contener números.")
    private String telefono;

    /** Lista de eventos asociados al cliente. */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Evento> eventos = new ArrayList<>();
}
