package edu.unc.eventos.services;

import edu.unc.eventos.domain.Rol;
import edu.unc.eventos.repositories.LocalRepository;
import edu.unc.eventos.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImp implements RolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> getAllRol() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> getRolById(Long idRol) {
        return rolRepository.findById(idRol);
    }

    @Override
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void deleteRol(Long idRol) {
        rolRepository.deleteById(idRol);
    }
}
