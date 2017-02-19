package com.cenfotec.escuelita.repository;

import com.cenfotec.escuelita.domain.Jugador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
public interface JugadorRepository extends JpaRepository<Jugador,Long> {
<<<<<<< HEAD

=======
    List<Jugador> findAllByCategoria_Id(Long id);
>>>>>>> cb0cae3b556143fea6f9e4e2033bfff3170b5285
}
