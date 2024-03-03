/**
 * @file: ApplicationConfig.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 06:53:06 PM
 */
package edu.unc.eventos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de la aplicación Spring Boot.
 * <p>
 * Esta clase contiene definiciones de beans que se utilizan en toda la aplicación.
 * Los beans definidos aquí se pueden inyectar en otras partes de la aplicación según sea necesario.
 * <p>
 * La anotación {@code @Configuration} indica a Spring que esta clase contiene definiciones de beans.
 */
@Configuration
public class ApplicationConfig {
    /**
     * {@code ModelMapper} es una biblioteca que simplifica el mapeo de objetos en Java.
     * Se utiliza a menudo para convertir entre objetos de dominio y DTOs.
     * <p>
     * La anotación {@code @Bean} indica que este método produce un bean que debe ser gestionado por el contenedor de Spring.
     *
     * @return una nueva instancia de {@code ModelMapper}.
     */
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
