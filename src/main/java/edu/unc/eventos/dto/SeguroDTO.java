/**
 * @file: SeguroDTO.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 6:50:00 PM
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Empleado;
import lombok.Data;
/**
 * La clase SeguroDTO es un objeto de transferencia de datos (DTO) que se utiliza para representar información
 * relacionada con seguros en el sistema. A diferencia de la clase Seguro, este DTO contiene solo los campos
 * relevantes para la comunicación entre capas o servicios.
 */

@Data
public class SeguroDTO {
    /**
     * El campo 'idSeguro' es el identificador único del seguro.
     */
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
    private Empleado empleado;
}
