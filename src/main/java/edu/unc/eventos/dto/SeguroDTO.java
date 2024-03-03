/**
 * @file: SeguroDTO.java
 * @author: (c)2024 Aldo Pereyra (AldoPM1305)
 * @created: Mar 03, 2024 18:50:00 PM
 * Data Transfer Object (DTO) para la entidad Seguro. Contiene información específica
 * para la transferencia de datos relacionados con seguros entre diferentes capas de la aplicación.
 * Utilizado para evitar la exposición innecesaria de detalles internos de la entidad Seguro.
 */
package edu.unc.eventos.dto;

import edu.unc.eventos.domain.Empleado;
import lombok.Data;

@Data
public class SeguroDTO {

    // Identificador único del seguro
    private Long idSeguro;

    // Código del seguro
    private String codigo;

    // Fecha de inscripción del seguro en formato de cadena (String)
    private String fechaIncripcion;

    // Empleado asociado al seguro
    private Empleado empleado;
}
