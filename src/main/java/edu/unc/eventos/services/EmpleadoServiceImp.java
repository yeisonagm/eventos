package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;
import edu.unc.eventos.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImp implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empleado> getEmpleadoById(Long idEmpleado) {
        return empleadoRepository.findById(idEmpleado);
    }

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public void deleteEmpleado(Long idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
    }
}
