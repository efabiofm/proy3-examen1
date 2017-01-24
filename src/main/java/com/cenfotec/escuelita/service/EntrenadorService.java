package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Entrenador;
import com.cenfotec.escuelita.repository.EntrenadorRepository;
import com.cenfotec.escuelita.service.dto.EntrenadorDTO;
import com.cenfotec.escuelita.service.mapper.EntrenadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Entrenador.
 */
@Service
@Transactional
public class EntrenadorService {

    private final Logger log = LoggerFactory.getLogger(EntrenadorService.class);
    
    @Inject
    private EntrenadorRepository entrenadorRepository;

    @Inject
    private EntrenadorMapper entrenadorMapper;

    /**
     * Save a entrenador.
     *
     * @param entrenadorDTO the entity to save
     * @return the persisted entity
     */
    public EntrenadorDTO save(EntrenadorDTO entrenadorDTO) {
        log.debug("Request to save Entrenador : {}", entrenadorDTO);
        Entrenador entrenador = entrenadorMapper.entrenadorDTOToEntrenador(entrenadorDTO);
        entrenador = entrenadorRepository.save(entrenador);
        EntrenadorDTO result = entrenadorMapper.entrenadorToEntrenadorDTO(entrenador);
        return result;
    }

    /**
     *  Get all the entrenadors.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EntrenadorDTO> findAll() {
        log.debug("Request to get all Entrenadors");
        List<EntrenadorDTO> result = entrenadorRepository.findAll().stream()
            .map(entrenadorMapper::entrenadorToEntrenadorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one entrenador by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EntrenadorDTO findOne(Long id) {
        log.debug("Request to get Entrenador : {}", id);
        Entrenador entrenador = entrenadorRepository.findOne(id);
        EntrenadorDTO entrenadorDTO = entrenadorMapper.entrenadorToEntrenadorDTO(entrenador);
        return entrenadorDTO;
    }

    /**
     *  Delete the  entrenador by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrenador : {}", id);
        entrenadorRepository.delete(id);
    }
}
