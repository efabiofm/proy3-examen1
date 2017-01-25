package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Entrenamiento;
import com.cenfotec.escuelita.repository.EntrenamientoRepository;
import com.cenfotec.escuelita.service.dto.EntrenamientoDTO;
import com.cenfotec.escuelita.service.mapper.EntrenamientoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Entrenamiento.
 */
@Service
@Transactional
public class EntrenamientoService {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoService.class);
    
    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    @Inject
    private EntrenamientoMapper entrenamientoMapper;

    /**
     * Save a entrenamiento.
     *
     * @param entrenamientoDTO the entity to save
     * @return the persisted entity
     */
    public EntrenamientoDTO save(EntrenamientoDTO entrenamientoDTO) {
        log.debug("Request to save Entrenamiento : {}", entrenamientoDTO);
        Entrenamiento entrenamiento = entrenamientoMapper.entrenamientoDTOToEntrenamiento(entrenamientoDTO);
        entrenamiento = entrenamientoRepository.save(entrenamiento);
        EntrenamientoDTO result = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);
        return result;
    }

    /**
     *  Get all the entrenamientos.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<EntrenamientoDTO> findAll() {
        log.debug("Request to get all Entrenamientos");
        List<EntrenamientoDTO> result = entrenamientoRepository.findAll().stream()
            .map(entrenamientoMapper::entrenamientoToEntrenamientoDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one entrenamiento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EntrenamientoDTO findOne(Long id) {
        log.debug("Request to get Entrenamiento : {}", id);
        Entrenamiento entrenamiento = entrenamientoRepository.findOne(id);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);
        return entrenamientoDTO;
    }

    /**
     *  Delete the  entrenamiento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrenamiento : {}", id);
        entrenamientoRepository.delete(id);
    }
}
