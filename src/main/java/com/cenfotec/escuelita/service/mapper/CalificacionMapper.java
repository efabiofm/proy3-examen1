package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.CalificacionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Calificacion and its DTO CalificacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CalificacionMapper {

    @Mapping(source = "jugador.id", target = "jugadorId")
    @Mapping(source = "entrenamiento.id", target = "entrenamientoId")
    CalificacionDTO calificacionToCalificacionDTO(Calificacion calificacion);

    List<CalificacionDTO> calificacionsToCalificacionDTOs(List<Calificacion> calificacions);

    @Mapping(source = "jugadorId", target = "jugador")
    @Mapping(source = "entrenamientoId", target = "entrenamiento")
    Calificacion calificacionDTOToCalificacion(CalificacionDTO calificacionDTO);

    List<Calificacion> calificacionDTOsToCalificacions(List<CalificacionDTO> calificacionDTOs);

    default Jugador jugadorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Jugador jugador = new Jugador();
        jugador.setId(id);
        return jugador;
    }

    default Entrenamiento entrenamientoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Entrenamiento entrenamiento = new Entrenamiento();
        entrenamiento.setId(id);
        return entrenamiento;
    }
}
