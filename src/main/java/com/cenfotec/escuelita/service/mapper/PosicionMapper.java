package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.PosicionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Posicion and its DTO PosicionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PosicionMapper {

    PosicionDTO posicionToPosicionDTO(Posicion posicion);

    List<PosicionDTO> posicionsToPosicionDTOs(List<Posicion> posicions);

    Posicion posicionDTOToPosicion(PosicionDTO posicionDTO);

    List<Posicion> posicionDTOsToPosicions(List<PosicionDTO> posicionDTOs);
}
