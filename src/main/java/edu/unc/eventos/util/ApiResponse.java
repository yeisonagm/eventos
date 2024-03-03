/**
 * @file: ApiResponse.java
 * @author: (c)2024 rocasari
 * @created: Mar 02, 2024 11:53:00 PM
 */
package edu.unc.eventos.util;

import lombok.Data;

/**
 * Clase que representa una respuesta genérica para las API.
 *
 * @param <T> Tipo de datos asociado a la respuesta.
 */
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**
     * Constructor para crear un objeto ApiResponse
     *
     * @param success Indica si la operación fue exitosa.
     * @param message Mensaje descriptivo de la respuesta.
     * @param data    Datos asociados a la respuesta.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
