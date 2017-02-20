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
    EntrenamientoDTO entrenamientoToEntrenamientoDTO(Entrenamiento entrenamiento);

    List<EntrenamientoDTO> entrenamientosToEntrenamientoDTOs(List<Entrenamiento> entrenamientos);

    @Mapping(source = "horarioId", target = "horario")
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
}
