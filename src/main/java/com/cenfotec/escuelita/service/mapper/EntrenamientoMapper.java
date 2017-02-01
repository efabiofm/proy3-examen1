package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.EntrenamientoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Entrenamiento and its DTO EntrenamientoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntrenamientoMapper {

    @Mapping(source = "horario.id", target = "horarioId")
    @Mapping(source = "horario.nombre", target = "horarioNombre")
    @Mapping(source = "entrenador.id", target = "entrenadorId")
    @Mapping(source = "entrenador.nombre", target = "entrenadorNombre")
    EntrenamientoDTO entrenamientoToEntrenamientoDTO(Entrenamiento entrenamiento);

    List<EntrenamientoDTO> entrenamientosToEntrenamientoDTOs(List<Entrenamiento> entrenamientos);

    @Mapping(source = "horarioId", target = "horario")
    @Mapping(source = "entrenadorId", target = "entrenador")
    Entrenamiento entrenamientoDTOToEntrenamiento(EntrenamientoDTO entrenamientoDTO);

    List<Entrenamiento> entrenamientoDTOsToEntrenamientos(List<EntrenamientoDTO> entrenamientoDTOs);

    default Horario horarioFromId(Long id) {
        if (id == null) {
            return null;
        }
        Horario horario = new Horario();
        horario.setId(id);
        return horario;
    }

    default Entrenador entrenadorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Entrenador entrenador = new Entrenador();
        entrenador.setId(id);
        return entrenador;
    }
}
