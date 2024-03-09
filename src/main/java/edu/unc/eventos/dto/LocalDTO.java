/**
 * @file: LocalDTO.java
 * @author:(c)2024 Peter Vásquez
 * @created: Mar 03, 2024 00:59:00 AM
 * @description: Esta clase es un DTO (Data Transfer Object) que se utiliza para transferir datos de la clase de dominio 'Local' entre diferentes capas de la aplicación, especialmente para operaciones de mapeo de datos.
 */
package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.unc.eventos.domain.Evento;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocalDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocal;

    /** Nombre del Local.
     * El campo Nombre es una descripción textual del local.
     * Este campo es obligatorio y su longitud debe estar entre 6 y 50 caracteres.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 6, max = 50, message = "El nombre debe tener entre 6 a 50 caracteres.")
    @Column(unique = true)
    private String nombre;

    /** Ubicaión del Local.
     * El campo ubicaión es una descripción textual del local.
     * Este campo es obligatorio y su longitud debe estar entre 10 y 50 caracteres.
     */
    @NotBlank(message = "La Ubicación no puede estar vacío.")
    @Size(min = 10, max = 50, message = "La ubicación debe tener entre 10 a 50 caracteres.")
    @Column(unique = true)
    private String ubicacion;

    /** Aforo del local.
     * El campo aforo representa la capacidad máxima de personas permitidas en el local.
     * Este campo es obligatorio y debe ser un número entre 10 y 500.
     */
    @NotNull(message = "El aforo no puede estar vacío.")
    @Min(value = 10, message = "El aforo debe ser mayor a 10.")
    @Max(value = 500, message = "El aforo debe ser menor que 500.")
    private Integer aforo;

    /** Referencia del Local.
     * El campo referencia es una descripción adicional del local.
     * Este campo puede estar vacío o tener una longitud entre 5 y 30 caracteres.
     */
    @Size(min = 5, max = 30, message = "La referencia debe tener entre 10 a 30 caracteres.")
    private String referencia;

    /** Relación con Evento.
     * El campo eventos representa la lista de eventos asociados al local.
     * Un local puede tener múltiples eventos asociados.
     */
    @OneToMany(mappedBy = "local")
    @JsonIgnore
    @JsonIdentityReference(alwaysAsId = true)
    private List<Evento> eventos = new ArrayList<>();
}
