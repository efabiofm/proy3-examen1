package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Categoria;
import com.cenfotec.escuelita.repository.CategoriaRepository;
import com.cenfotec.escuelita.service.dto.CategoriaDTO;
import com.cenfotec.escuelita.service.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Categoria.
 */
@Service
@Transactional
public class CategoriaService {

    private final Logger log = LoggerFactory.getLogger(CategoriaService.class);
    
    @Inject
    private CategoriaRepository categoriaRepository;

    @Inject
    private CategoriaMapper categoriaMapper;

    /**
     * Save a categoria.
     *
     * @param categoriaDTO the entity to save
     * @return the persisted entity
     */
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        log.debug("Request to save Categoria : {}", categoriaDTO);
        Categoria categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO);
        categoria = categoriaRepository.save(categoria);
        CategoriaDTO result = categoriaMapper.categoriaToCategoriaDTO(categoria);
        return result;
    }

    /**
     *  Get all the categorias.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<CategoriaDTO> findAll() {
        log.debug("Request to get all Categorias");
        List<CategoriaDTO> result = categoriaRepository.findAll().stream()
            .map(categoriaMapper::categoriaToCategoriaDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one categoria by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public CategoriaDTO findOne(Long id) {
        log.debug("Request to get Categoria : {}", id);
        Categoria categoria = categoriaRepository.findOne(id);
        CategoriaDTO categoriaDTO = categoriaMapper.categoriaToCategoriaDTO(categoria);
        return categoriaDTO;
    }

    /**
     *  Delete the  categoria by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Categoria : {}", id);
        categoriaRepository.delete(id);
    }
}
