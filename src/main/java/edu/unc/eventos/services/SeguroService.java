package edu.unc.eventos.services;

import edu.unc.eventos.domain.Seguro;

import java.util.List;
import java.util.Optional;

public interface SeguroService {
    List<Seguro> getAllSeguro();

    Optional<Seguro> getSeguroById(Long idSeguro);

    Seguro saveSeguro(Seguro seguro);

    void deleteSeguro(Long idSeguro);
}
