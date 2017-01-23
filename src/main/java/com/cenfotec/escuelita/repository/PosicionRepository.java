package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Posicion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Posicion entity.
 */
@SuppressWarnings("unused")
public interface PosicionRepository extends JpaRepository<Posicion,Long> {

}
