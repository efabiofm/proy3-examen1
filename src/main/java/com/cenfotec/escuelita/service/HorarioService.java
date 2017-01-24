package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Horario;
import com.cenfotec.escuelita.repository.HorarioRepository;
import com.cenfotec.escuelita.service.dto.HorarioDTO;
import com.cenfotec.escuelita.service.mapper.HorarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Horario.
 */
@Service
@Transactional
public class HorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);
    
    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private HorarioMapper horarioMapper;

    /**
     * Save a horario.
     *
     * @param horarioDTO the entity to save
     * @return the persisted entity
     */
    public HorarioDTO save(HorarioDTO horarioDTO) {
        log.debug("Request to save Horario : {}", horarioDTO);
        Horario horario = horarioMapper.horarioDTOToHorario(horarioDTO);
        horario = horarioRepository.save(horario);
        HorarioDTO result = horarioMapper.horarioToHorarioDTO(horario);
        return result;
    }

    /**
     *  Get all the horarios.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<HorarioDTO> findAll() {
        log.debug("Request to get all Horarios");
        List<HorarioDTO> result = horarioRepository.findAll().stream()
            .map(horarioMapper::horarioToHorarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one horario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public HorarioDTO findOne(Long id) {
        log.debug("Request to get Horario : {}", id);
        Horario horario = horarioRepository.findOne(id);
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);
        return horarioDTO;
    }

    /**
     *  Delete the  horario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Horario : {}", id);
        horarioRepository.delete(id);
    }
}
