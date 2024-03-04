/**
 * @file: PlatoController.java
 * @author: (c)2024 Peter Vásquez
 * @created: Mar 03, 2024 09:11:11 PM
 */
package edu.unc.eventos.controllers;

import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.dto.PlatoDTO;
import edu.unc.eventos.services.PlatoService;
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
 * Controlador REST que gestiona las operaciones CRUD para 'Plato'.
 * <p>Se mapea a la ruta '/api/platos' y espera que todas las solicitudes incluyan el encabezado 'Api-Version=1'.
 * Las operaciones incluyen obtener todas las decoraciones, obtener una decoración por su ID,
 * guardar una nueva decoración, actualizar una decoración existente y eliminar un plato.</p>
 */
@RestController
@RequestMapping(value = "/api/platos", headers = "Api-Version=1")
public class PlatoController {
    @Autowired
    private PlatoService platoService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Obtiene todos los Platos existentes
     *
     * @return Lista de Platos
     */
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Plato> platos = platoService.getAllPlato();
        if(platos == null || platos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            List<PlatoDTO> PlatosDTOs = platos.stream()
                    .map(Plato -> modelMapper.map(Plato, PlatoDTO.class))
                    .collect(Collectors.toList());
            ApiResponse<List<PlatoDTO>> response = new ApiResponse<>(true,"Lista de Platoes",PlatosDTOs);
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
        Optional<Plato> PlatoOpt = platoService.getPlatoById(id);
        if(PlatoOpt.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        PlatoDTO PlatoDTO = modelMapper.map(PlatoOpt.get(),PlatoDTO.class);
        ApiResponse<PlatoDTO> response = new ApiResponse<>(true,"Plato",PlatoDTO);
        return ResponseEntity.ok(response);
    }
}
