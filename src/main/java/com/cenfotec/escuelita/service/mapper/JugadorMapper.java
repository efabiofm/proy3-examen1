package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.JugadorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Jugador and its DTO JugadorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JugadorMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    @Mapping(source = "posicion.id", target = "posicionId")
    @Mapping(source = "posicion.nombre", target = "posicionNombre")
    JugadorDTO jugadorToJugadorDTO(Jugador jugador);

    List<JugadorDTO> jugadorsToJugadorDTOs(List<Jugador> jugadors);

    @Mapping(source = "categoriaId", target = "categoria")
    @Mapping(source = "posicionId", target = "posicion")
    Jugador jugadorDTOToJugador(JugadorDTO jugadorDTO);

    List<Jugador> jugadorDTOsToJugadors(List<JugadorDTO> jugadorDTOs);

    default Categoria categoriaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }

    default Posicion posicionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Posicion posicion = new Posicion();
        posicion.setId(id);
        return posicion;
    }
}
