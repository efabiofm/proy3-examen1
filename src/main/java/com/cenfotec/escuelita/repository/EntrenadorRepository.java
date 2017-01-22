package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Entrenador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Entrenador entity.
 */
@SuppressWarnings("unused")
public interface EntrenadorRepository extends JpaRepository<Entrenador,Long> {

}
