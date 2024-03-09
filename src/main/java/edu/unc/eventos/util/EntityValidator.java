/**
 * @file: EntityValidator.java
 * @author: (c)2024 Yeison García
 * @created: Mar 04, 2024 06:30:12 PM
 */
package edu.unc.eventos.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que se encarga de validar las entidades y devolver los errores de validación.
 */
public class EntityValidator {
    /**
     * Método que recibe un resultado de validación y devuelve un ResponseEntity con los errores.
     *
     * @param result Resultado de validación.
     * @return ResponseEntity con los errores de validación.
     */
    public ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "Error [" + error.getField() + "]: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
