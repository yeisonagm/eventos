package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;

import java.util.List;
import java.util.Optional;

public class LocalServiceImp implements LocalService{
    @Override
    public List<Local> getAllLocal() {
        return null;
    }

    @Override
    public Optional<Local> getLocalById(Long idLocal) {
        return Optional.empty();
    }

    @Override
    public Local saveLocal(Local local) {
        return null;
    }

    @Override
    public void deleteLocal(Long idLocal) {

    }
}
