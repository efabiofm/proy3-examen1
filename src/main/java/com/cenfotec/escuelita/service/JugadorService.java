package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Jugador;
import com.cenfotec.escuelita.repository.JugadorRepository;
import com.cenfotec.escuelita.service.dto.JugadorDTO;
import com.cenfotec.escuelita.service.mapper.JugadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Jugador.
 */
@Service
@Transactional
public class JugadorService {

    private final Logger log = LoggerFactory.getLogger(JugadorService.class);

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private JugadorMapper jugadorMapper;

    /**
     * Save a jugador.
     *
     * @param jugadorDTO the entity to save
     * @return the persisted entity
     */
    public JugadorDTO save(JugadorDTO jugadorDTO) {
        log.debug("Request to save Jugador : {}", jugadorDTO);
        Jugador jugador = jugadorMapper.jugadorDTOToJugador(jugadorDTO);
        jugador = jugadorRepository.save(jugador);
        JugadorDTO result = jugadorMapper.jugadorToJugadorDTO(jugador);
        return result;
    }

    /**
     *  Get all the jugadors.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<JugadorDTO> findAll() {
        log.debug("Request to get all Jugadors");
        List<JugadorDTO> result = jugadorRepository.findAll().stream()
            .map(jugadorMapper::jugadorToJugadorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one jugador by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public JugadorDTO findOne(Long id) {
        log.debug("Request to get Jugador : {}", id);
        Jugador jugador = jugadorRepository.findOne(id);
        JugadorDTO jugadorDTO = jugadorMapper.jugadorToJugadorDTO(jugador);
        return jugadorDTO;
    }

    /**
     *  Delete the  jugador by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Jugador : {}", id);
        jugadorRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<JugadorDTO> findAllByCategoria(Long id) {
        List<JugadorDTO> result = jugadorRepository.findByCategoria_Id(id).stream()
            .map(jugadorMapper::jugadorToJugadorDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
}
