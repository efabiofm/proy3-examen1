package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Jugador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
    List<Jugador> findByCategoria_Id(Long id);
}
