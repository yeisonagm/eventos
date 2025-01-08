package edu.unc.eventos.exception;

import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Gestiona las excepciones globales, permite controlar y personalizar las respuestas de errores en la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja la excepción EntityNotFoundException.
     *
     * @param ex      la excepción lanzada.
     * @param request la solicitud web que resultó en una excepción.
     * @return una respuesta HTTP personalizada con detalles de error.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción IllegalOperationException.
     *
     * @param ex      la excepción lanzada.
     * @param request la solicitud web que resultó en una excepción.
     * @return una respuesta HTTP personalizada.
     */
    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ErrorMessage> handleIllegalOperationException(IllegalOperationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja la excepción FeignException.
     *
     * @param e la excepción Feign.
     * @return una respuesta HTTP personalizada con detalles de error.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeignStatusException(FeignException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.valueOf(e.status()), "Acceso denegado", e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.valueOf(e.status()));
    }

    /**
     * Maneja cualquier excepción no específica.
     *
     * @param ex      la excepción lanzada.
     * @param request la solicitud web asociada.
     * @return una respuesta HTTP personalizada con detalles de error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}