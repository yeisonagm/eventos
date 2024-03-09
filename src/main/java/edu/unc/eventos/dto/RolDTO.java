/**
 * @file: RolDTO.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 05:22:48 PM
 */
package edu.unc.eventos.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase RolDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con roles en el sistema. A diferencia de la clase Rol, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idRol")
public class RolDTO {
    /**
     * El campo idRol es el identificador único de cada Rol en la base de datos.
     * Este campo es generado automáticamente por la base de datos cuando se crea un nuevo Rol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    /**
     * El campo Nombre es una descripción textual del Rol. Este campo es obligatorio y su longitud debe estar entre 2 y 15 caracteres.
     * Este campo ayuda a identificar el Rol de manera más amigable y comprensible para los usuarios del sistema.
     */
    @NotBlank(message = "El Nombre no puede estar vacío.")
    @Size(min = 2, max = 15, message = "El nombre debe tener entre 4 a 15 caracteres.")
    @Column(unique = true)
    private String nombre;

    /**
     * El campo Empleados es una lista de todos los empleados que están asociados a este Rol.
     * Esta lista puede estar vacía, lo que significa que actualmente no hay empleados que tengan este Rol.
     * Cuando un empleado es asignado a este Rol, se añade a esta lista.
     */
    @JsonIdentityReference(alwaysAsId = true)
    private List<EmpleadoDTO> empleados = new ArrayList<>();
}
