package edu.unc.eventos.services;

import edu.unc.eventos.domain.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> getAllEmpleados();

    Optional<Empleado> getEmpleadoById(Long idEmpleado);

    Empleado saveEmpleado(Empleado empleado);

    void deleteEmpleado(Long idEmpleado);
}
