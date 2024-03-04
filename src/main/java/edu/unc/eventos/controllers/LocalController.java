/**
 * @file: LocalController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 08:53:18 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.dto.LocalDTO;
import edu.unc.eventos.services.LocalService;
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
 * Controlador REST que gestiona las operaciones CRUD para 'Local'.
 * <p>Se mapea a la ruta '/api/locales' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todas las decoraciones, obtener una decoración por su ID,
 * guardar una nueva decoración, actualizar una decoración existente y eliminar una decoración.</p>
 */
@RestController
@RequestMapping(value = "/api/locales", headers = "Api-Version=1")
public class LocalController {
    @Autowired
    private LocalService localService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los locales existentes
     *
     * @return Lista de locales
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Local> locales = localService.getAllLocal();
        if(locales == null || locales.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            List<LocalDTO> localesDTOs = locales.stream()
                    .map(local -> modelMapper.map(local, LocalDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<LocalDTO>> response = new ApiResponse<>(true,"Lista de locales",localesDTOs);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Obtiene un plato por su identificador
     *
     * @param id Identificador del plato
     * @return plato
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Local> localOpt = localService.getLocalById(id);
        if(localOpt.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        LocalDTO localDTO = modelMapper.map(localOpt.get(),LocalDTO.class);
        ApiResponse<LocalDTO> response = new ApiResponse<>(true,"Local",localDTO);
        return ResponseEntity.ok(response);
    }
}
