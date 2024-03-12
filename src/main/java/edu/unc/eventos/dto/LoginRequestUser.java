/**
 * @file: Usuario.java
 * @author: (c)2024 Yeison García
 * @created: Mar 10, 2024 05:13:10 AM
 */
package edu.unc.eventos.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Clase que representa el DTO de login de Usuario.
 * <p>
 * Esta clase es un DTO que se utiliza para recibir información de login de un usuario.
 * La anotación {@code @Data} es una anotación de Lombok que genera automáticamente getters, setters, toString, equals y hashCode.
 */
@Data
public class LoginRequestUser {
    /**
     * El email del usuario.
     */
    @NotBlank(message = "Ingrese su email.")
    @Column(unique = true)
    @Size(max = 30, message = "El email debe tener menos de 30 caracteres.")
    @Email(message = "El email debe ser válido.")
    private String email;

    /**
     * La contraseña del usuario.
     */
    @NotBlank(message = "Ingrese su contraseña.")
    @Size(min = 6, max = 24, message = "La contraseña debe tener entre 6 a 24 caracteres.")
    private String password;
}