/**
 * @file: EntityValidator.java
 * @author: (c)2024 Yeison García
 * @created: Mar 04, 2024 06:30:12 PM
 */
package edu.unc.eventos.util;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase que se encarga de validar las entidades y devolver los errores de validación.
 */
public class EntityValidator {
    /**
     * Método que recibe un conjunto de violaciones de restricciones de validación y devuelve un ResponseEntity con los errores.
     *
     * @param violations Conjunto de violaciones de restricciones de validación.
     * @param <T>        Tipo de la entidad que se está validando.
     * @return ResponseEntity con los errores de validación.
     */
    public <T> ResponseEntity<Map<String, String>> validate(Set<ConstraintViolation<T>> violations) {
        Map<String, String> errores = new HashMap<>();
        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errores.put(field, "Error de validación [" + field + "]: " + message);
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
