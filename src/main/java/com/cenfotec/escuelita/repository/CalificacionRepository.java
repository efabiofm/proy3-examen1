package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Calificacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Calificacion entity.
 */
@SuppressWarnings("unused")
public interface CalificacionRepository extends JpaRepository<Calificacion,Long> {
    List<Calificacion> findByEntrenamiento_Id(Long idEntrenamiento);
}
