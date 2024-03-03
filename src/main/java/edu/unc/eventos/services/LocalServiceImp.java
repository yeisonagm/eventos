package edu.unc.eventos.services;

import edu.unc.eventos.domain.Local;
import edu.unc.eventos.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocalServiceImp implements LocalService {
    @Autowired
    private LocalRepository localRepository;

    @Override
    public List<Local> getAllLocal() {
        return localRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Local> getLocalById(Long idLocal) {
        return localRepository.findById(idLocal);
    }

    @Override
    public Local saveLocal(Local local) {
        return localRepository.save(local);
    }

    @Override
    public void deleteLocal(Long idLocal) {
        localRepository.deleteById(idLocal);
    }
}
