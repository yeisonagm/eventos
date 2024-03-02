package edu.unc.eventos.services;

import edu.unc.eventos.domain.Seguro;

import java.util.List;
import java.util.Optional;

public class SeguroServiceImp implements SeguroService{
    @Override
    public List<Seguro> getAllSeguro() {
        return null;
    }

    @Override
    public Optional<Seguro> getSeguroById(Long idSeguro) {
        return Optional.empty();
    }

    @Override
    public Seguro saveSeguro(Seguro seguro) {
        return null;
    }

    @Override
    public void deleteSeguro(Long idSeguro) {

    }
}
