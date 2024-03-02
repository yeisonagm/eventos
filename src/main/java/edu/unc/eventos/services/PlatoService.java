package edu.unc.eventos.services;

import edu.unc.eventos.domain.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> getAllPlato();

    Optional<Plato> getPlatoById(Long idPlato);

    Plato savePlato(Plato plato);

    void deletePlato(Long idPlato);
}