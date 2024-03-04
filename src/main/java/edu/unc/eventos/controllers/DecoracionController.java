/**
 * @file: ApplicationConfig.java
 * @author: (c)2024 Yeison García
 * @created: Mar 03, 2024 07:20:18 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.dto.DecoracionDTO;
import edu.unc.eventos.services.DecoracionService;
import edu.unc.eventos.util.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST que gestiona las operaciones CRUD para 'Decoraciones'.
 * <p>Se mapea a la ruta '/api/decoraciones' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todas las decoraciones, obtener una decoración por su ID,
 * guardar una nueva decoración, actualizar una decoración existente y eliminar una decoración.</p>
 */
@RestController
@RequestMapping(value = "/api/decoraciones", headers = "Api-Version=1")
public class DecoracionController {
    @Autowired
    private DecoracionService decoracionService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todas las decoraciones existentes
     *
     * @return Lista de decoraciones
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Decoracion> decoraciones = decoracionService.getAllDecoraciones();
        if (decoraciones == null || decoraciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<DecoracionDTO> decoracionesDTOs = decoraciones.stream()
                    .map(decoracion -> modelMapper.map(decoracion, DecoracionDTO.class))
                    .collect(Collectors.toList());

            ApiResponse<List<DecoracionDTO>> response = new ApiResponse<>(true, "Lista de decoraciones", decoracionesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene una decoración por su identificador
     *
     * @param id Identificador de la decoración
     * @return Decoración
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Decoracion> decoracionOpt = decoracionService.getDecoracionById(id);
        if (decoracionOpt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        DecoracionDTO decoracionDTO = modelMapper.map(decoracionOpt.get(), DecoracionDTO.class);
        ApiResponse<DecoracionDTO> response = new ApiResponse<>(true, "Decoración", decoracionDTO);
        return ResponseEntity.ok(response);
    }

}
