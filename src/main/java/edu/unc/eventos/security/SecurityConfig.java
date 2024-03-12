package edu.unc.eventos.security;

import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Clase que representa la configuración de seguridad.
 * <p>
 * Esta clase es una configuración de seguridad que se utiliza para definir la configuración de seguridad.
 */
@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//    private final AuthenticationFilter authenticationFilter;
//
//    /**
//     * Constructor de la configuración de seguridad.
//     *
//     * @param authenticationFilter El filtro de autenticación.
//     */
//    public SecurityConfig(AuthenticationFilter authenticationFilter) {
//        this.authenticationFilter = authenticationFilter;
//    }

    /**
     * Configuración de la seguridad.
     *
     * @param http El objeto HttpSecurity.
     * @throws Exception Si ocurre un error al configurar la seguridad.
     */
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeRequests()
//                .anyRequest().permitAll();
//                .addFilter(authenticationFilter)
//                .authorizeRequests()
//                .anyRequest().authenticated();
//    }
}