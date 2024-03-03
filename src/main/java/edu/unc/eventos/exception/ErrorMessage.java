/**
 * @file: ErrorMessage.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 01:32:04 AM
 */
package edu.unc.eventos.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Clase que representa un mensaje de error personalizado.
 * Excepción que se lanza cuando se realiza una operación ilegal.
 */
@Getter
@Setter
public class ErrorMessage {
    /**
     * Código de estado HTTP de la operación.
     */
    private int statusCode;
    /**
     * Fecha y hora en que se generó el error.
     */
    private LocalDateTime timestamp;
    /**
     * Mensaje descriptivo del error.
     */
    private String message;
    /**
     * Descripción detallada del error.
     */
    private String description;

    /**
     * Constructor de la clase ErrorMessage.
     *
     * @param statusCode  Código de estado HTTP asociado al error.
     * @param message     Mensaje preciso del error.
     * @param description Descripción detallada del error.
     */
    public ErrorMessage(HttpStatus statusCode, String message, String description) {
        this.statusCode = statusCode.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.description = description;
    }
}
