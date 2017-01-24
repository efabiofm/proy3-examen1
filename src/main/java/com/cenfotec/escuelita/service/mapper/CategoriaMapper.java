package com.cenfotec.escuelita.service.mapper;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.service.dto.CategoriaDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper {

    CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

    List<CategoriaDTO> categoriasToCategoriaDTOs(List<Categoria> categorias);

    Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO);

    List<Categoria> categoriaDTOsToCategorias(List<CategoriaDTO> categoriaDTOs);
}
