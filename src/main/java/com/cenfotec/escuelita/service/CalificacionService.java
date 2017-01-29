package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Calificacion;
import com.cenfotec.escuelita.repository.CalificacionRepository;
import com.cenfotec.escuelita.service.dto.CalificacionDTO;
import com.cenfotec.escuelita.service.mapper.CalificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Calificacion.
 */
@Service
@Transactional
public class CalificacionService {

    private final Logger log = LoggerFactory.getLogger(CalificacionService.class);

    @Inject
    private CalificacionRepository calificacionRepository;

    @Inject
    private CalificacionMapper calificacionMapper;

    /**
     * Save a calificacion.
     *
     * @param calificacionDTO the entity to save
     * @return the persisted entity
     */
    public CalificacionDTO save(CalificacionDTO calificacionDTO) {
        log.debug("Request to save Calificacion : {}", calificacionDTO);
        Calificacion calificacion = calificacionMapper.calificacionDTOToCalificacion(calificacionDTO);
        calificacion = calificacionRepository.save(calificacion);
        CalificacionDTO result = calificacionMapper.calificacionToCalificacionDTO(calificacion);
        return result;
    }

    /**
     *  Get all the calificacions.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<CalificacionDTO> findAll() {
        log.debug("Request to get all Calificacions");
        List<CalificacionDTO> result = calificacionRepository.findAll().stream()
            .map(calificacionMapper::calificacionToCalificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one calificacion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CalificacionDTO findOne(Long id) {
        log.debug("Request to get Calificacion : {}", id);
        Calificacion calificacion = calificacionRepository.findOne(id);
        CalificacionDTO calificacionDTO = calificacionMapper.calificacionToCalificacionDTO(calificacion);
        return calificacionDTO;
    }

    /**
     *  Delete the  calificacion by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Calificacion : {}", id);
        calificacionRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<CalificacionDTO> findAllByEntrenamiento(Long id) {
        log.debug("Request to get all Items");
        List<CalificacionDTO> result = calificacionRepository.findByEntrenamiento_Id(id).stream()
            .map(calificacionMapper::calificacionToCalificacionDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
