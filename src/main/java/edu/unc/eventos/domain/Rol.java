/**
 * @file: Rol.java
 * @author: (c)2024 Roberto Salazar (rocasari)
 * @created: Mar 03, 2024 00:10:00 AM
 */
package edu.unc.eventos.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Rol es una entidad que representa el rol que un empleado puede tener en el sistema.
 * Un rol es una forma de categorizar a los empleados según sus responsabilidades y privilegios en el sistema.
 * Cada instancia de Rol tiene un identificador único, un nombre y una lista de empleados asociados.
 */
@Entity
@Data
public class Rol {
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
    private String nombre;

    /**
     * El campo Empleados es una lista de todos los empleados que están asociados a este Rol.
     * Esta lista puede estar vacía, lo que significa que actualmente no hay empleados que tengan este Rol.
     * Cuando un empleado es asignado a este Rol, se añade a esta lista.
     */
    @OneToMany(mappedBy = "rol")
    private List<Empleado> empleados = new ArrayList<>();
}