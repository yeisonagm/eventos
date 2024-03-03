package edu.unc.eventos.services;

import edu.unc.eventos.domain.Seguro;
import edu.unc.eventos.repositories.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguroServiceImp implements SeguroService{
    @Autowired
    private SeguroRepository seguroRepository
    @Override
    public List<Seguro> getAllSeguro() {
        return (List<Seguro>) seguroRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Seguro> getSeguroById(Long idSeguro) {
        return seguroRepository.findById(idSeguro);
    }

    @Override
    public Seguro saveSeguro(Seguro seguro) {
        return seguroRepository.save(seguro);
    }

    @Override
    public void deleteSeguro(Long idSeguro) {
        seguroRepository.deleteById(idSeguro);
    }
}
