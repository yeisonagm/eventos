/**
 * @file: Seguro.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 17:28:00 PM
 * Representa a un seguro en el contexto de un dominio específico.
 * Esta clase está anotada para persistencia y validación mediante anotaciones JPA.
 */
package edu.unc.eventos.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import lombok.Data;



@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idSeguro")
public class Seguro {
    /**
     * El campo 'idSeguro' es el identificador único del seguro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeguro;

    /**
     * El campo 'codigo' representa el código del seguro.
     */
    private String codigo;

    /**
     * El campo 'fechaInscripcion' representa la fecha de inscripción del seguro.
     */
    private String fechaInscripcion;

    /**
     * El campo 'empleado' representa el empleado asociado al seguro.
     */
    @OneToOne
    private Empleado empleado;
}
