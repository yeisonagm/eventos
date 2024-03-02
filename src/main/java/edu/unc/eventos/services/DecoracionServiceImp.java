package edu.unc.eventos.services;

import edu.unc.eventos.domain.Decoracion;
import edu.unc.eventos.repositories.DecoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DecoracionServiceImp implements DecoracionService {

    @Autowired
    private DecoracionRepository decoracionRepository;

    @Override
    public List<Decoracion> getAllDecoraciones() {
        return (List<Decoracion>) decoracionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Decoracion> getDecoracionById(Long idDecoracion) {
        return decoracionRepository.findById(idDecoracion);
    }

    @Override
    public Decoracion saveDecoracion(Decoracion decoracion) {
        return decoracionRepository.save(decoracion);
    }

    @Override
    public void deleteDecoracion(Long idDecoracion) {
        decoracionRepository.deleteById(idDecoracion);
    }
}
