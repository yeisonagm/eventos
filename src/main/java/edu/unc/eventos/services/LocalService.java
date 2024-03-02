package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;

import java.util.List;
import java.util.Optional;

public interface LocalService {
    List<Local> getAllLocal();

    Optional<Local> getLocalById(Long idLocal);

    Local saveLocal(Local local);

    void deleteLocal(Long idLocal);
}
