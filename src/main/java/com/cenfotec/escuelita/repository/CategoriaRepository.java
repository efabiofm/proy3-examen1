package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Categoria;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Categoria entity.
 */
@SuppressWarnings("unused")
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
