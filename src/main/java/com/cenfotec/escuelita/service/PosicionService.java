package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Posicion;
import com.cenfotec.escuelita.repository.PosicionRepository;
import com.cenfotec.escuelita.service.dto.PosicionDTO;
import com.cenfotec.escuelita.service.mapper.PosicionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Posicion.
 */
@Service
@Transactional
public class PosicionService {

    private final Logger log = LoggerFactory.getLogger(PosicionService.class);
    
    @Inject
    private PosicionRepository posicionRepository;

    @Inject
    private PosicionMapper posicionMapper;

    /**
     * Save a posicion.
     *
     * @param posicionDTO the entity to save
     * @return the persisted entity
     */
    public PosicionDTO save(PosicionDTO posicionDTO) {
        log.debug("Request to save Posicion : {}", posicionDTO);
        Posicion posicion = posicionMapper.posicionDTOToPosicion(posicionDTO);
        posicion = posicionRepository.save(posicion);
        PosicionDTO result = posicionMapper.posicionToPosicionDTO(posicion);
        return result;
    }

    /**
     *  Get all the posicions.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PosicionDTO> findAll() {
        log.debug("Request to get all Posicions");
        List<PosicionDTO> result = posicionRepository.findAll().stream()
            .map(posicionMapper::posicionToPosicionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one posicion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PosicionDTO findOne(Long id) {
        log.debug("Request to get Posicion : {}", id);
        Posicion posicion = posicionRepository.findOne(id);
        PosicionDTO posicionDTO = posicionMapper.posicionToPosicionDTO(posicion);
        return posicionDTO;
    }

    /**
     *  Delete the  posicion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Posicion : {}", id);
        posicionRepository.delete(id);
    }
}
