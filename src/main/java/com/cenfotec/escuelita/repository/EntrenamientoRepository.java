package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Entrenamiento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Entrenamiento entity.
 */
@SuppressWarnings("unused")
public interface EntrenamientoRepository extends JpaRepository<Entrenamiento,Long> {

    List<Entrenamiento> findAllByEntrenador_Id(Long id);
}
