/**
 * @file: Usuario.java
 * @author: (c)2024 Yeison García
 * @created: Mar 10, 2024 05:13:10 AM
 */
package edu.unc.eventos.services;


import edu.unc.eventos.dto.LoginRequestUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthService {
    /**
     * Inicia sesión en el sistema
     *
     * @param loginRequestUser La información de login del usuario
     * @return El token de autenticación
     */
    @PostMapping("/auth/login")
    String login(@RequestBody LoginRequestUser loginRequestUser);

    /**
     * Valida el token de autenticación
     *
     * @param authHeader El token de autenticación
     * @return {@code true} si el token es válido, {@code false} en caso contrario
     */
    @GetMapping("/auth/validate")
    boolean validateToken(@RequestHeader("Authorization") String authHeader, @RequestHeader("Api-Version") String apiVersion);
}
