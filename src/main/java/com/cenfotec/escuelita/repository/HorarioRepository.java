package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Horario;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Horario entity.
 */
@SuppressWarnings("unused")
public interface HorarioRepository extends JpaRepository<Horario,Long> {

}
