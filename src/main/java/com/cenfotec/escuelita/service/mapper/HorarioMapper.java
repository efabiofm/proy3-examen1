package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.HorarioDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Horario and its DTO HorarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HorarioMapper {

    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    HorarioDTO horarioToHorarioDTO(Horario horario);

    List<HorarioDTO> horariosToHorarioDTOs(List<Horario> horarios);

    @Mapping(source = "categoriaId", target = "categoria")
    Horario horarioDTOToHorario(HorarioDTO horarioDTO);

    List<Horario> horarioDTOsToHorarios(List<HorarioDTO> horarioDTOs);

    default Categoria categoriaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
}
