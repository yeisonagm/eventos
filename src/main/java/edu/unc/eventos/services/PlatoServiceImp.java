package edu.unc.eventos.services;

import edu.unc.eventos.domain.Plato;
import edu.unc.eventos.repositories.LocalRepository;
import edu.unc.eventos.repositories.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImp implements PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    @Override
    public List<Plato> getAllPlato() {
        return platoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plato> getPlatoById(Long idPlato) {
        return platoRepository.findById(idPlato);
    }

    @Override
    public Plato savePlato(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public void deletePlato(Long idPlato) {
        platoRepository.deleteById(idPlato);
    }
}
