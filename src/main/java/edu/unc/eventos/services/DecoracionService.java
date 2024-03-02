package edu.unc.eventos.services;

import edu.unc.eventos.domain.Decoracion;

import java.util.List;
import java.util.Optional;

public interface DecoracionService {
    List<Decoracion> getAllDecoraciones();

    Optional<Decoracion> getDecoracionById(Long idDecoracion);

    Decoracion saveDecoracion(Decoracion decoracion);

    void deleteDecoracion(Long idDecoracion);
}
