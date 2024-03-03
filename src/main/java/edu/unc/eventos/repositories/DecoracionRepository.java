package edu.unc.eventos.repositories;


import edu.unc.eventos.domain.Cliente;
import edu.unc.eventos.domain.Decoracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecoracionRepository extends JpaRepository<Decoracion, Long> {
    List<Decoracion> findByDescripcion(String descripcion);
    List<Decoracion> findByPrecioBetween(Double minPrice, Double maxPrice);
    List<Decoracion> findByColor(String color);

}
