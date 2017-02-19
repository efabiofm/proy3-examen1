package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.EntrenadorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Entrenador and its DTO EntrenadorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EntrenadorMapper {

    EntrenadorDTO entrenadorToEntrenadorDTO(Entrenador entrenador);

    List<EntrenadorDTO> entrenadorsToEntrenadorDTOs(List<Entrenador> entrenadors);

    Entrenador entrenadorDTOToEntrenador(EntrenadorDTO entrenadorDTO);

    List<Entrenador> entrenadorDTOsToEntrenadors(List<EntrenadorDTO> entrenadorDTOs);
}
