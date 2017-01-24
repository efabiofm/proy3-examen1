package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.service.EntrenadorService;
import com.cenfotec.escuelita.web.rest.util.HeaderUtil;
import com.cenfotec.escuelita.service.dto.EntrenadorDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Entrenador.
 */
@RestController
@RequestMapping("/api")
public class EntrenadorResource {

    private final Logger log = LoggerFactory.getLogger(EntrenadorResource.class);
        
    @Inject
    private EntrenadorService entrenadorService;

    /**
     * POST  /entrenadors : Create a new entrenador.
     *
     * @param entrenadorDTO the entrenadorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrenadorDTO, or with status 400 (Bad Request) if the entrenador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrenadors")
    @Timed
    public ResponseEntity<EntrenadorDTO> createEntrenador(@Valid @RequestBody EntrenadorDTO entrenadorDTO) throws URISyntaxException {
        log.debug("REST request to save Entrenador : {}", entrenadorDTO);
        if (entrenadorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entrenador", "idexists", "A new entrenador cannot already have an ID")).body(null);
        }
        EntrenadorDTO result = entrenadorService.save(entrenadorDTO);
        return ResponseEntity.created(new URI("/api/entrenadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entrenador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrenadors : Updates an existing entrenador.
     *
     * @param entrenadorDTO the entrenadorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrenadorDTO,
     * or with status 400 (Bad Request) if the entrenadorDTO is not valid,
     * or with status 500 (Internal Server Error) if the entrenadorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrenadors")
    @Timed
    public ResponseEntity<EntrenadorDTO> updateEntrenador(@Valid @RequestBody EntrenadorDTO entrenadorDTO) throws URISyntaxException {
        log.debug("REST request to update Entrenador : {}", entrenadorDTO);
        if (entrenadorDTO.getId() == null) {
            return createEntrenador(entrenadorDTO);
        }
        EntrenadorDTO result = entrenadorService.save(entrenadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entrenador", entrenadorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrenadors : get all the entrenadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entrenadors in body
     */
    @GetMapping("/entrenadors")
    @Timed
    public List<EntrenadorDTO> getAllEntrenadors() {
        log.debug("REST request to get all Entrenadors");
        return entrenadorService.findAll();
    }

    /**
     * GET  /entrenadors/:id : get the "id" entrenador.
     *
     * @param id the id of the entrenadorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrenadorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/entrenadors/{id}")
    @Timed
    public ResponseEntity<EntrenadorDTO> getEntrenador(@PathVariable Long id) {
        log.debug("REST request to get Entrenador : {}", id);
        EntrenadorDTO entrenadorDTO = entrenadorService.findOne(id);
        return Optional.ofNullable(entrenadorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entrenadors/:id : delete the "id" entrenador.
     *
     * @param id the id of the entrenadorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrenadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrenador(@PathVariable Long id) {
        log.debug("REST request to delete Entrenador : {}", id);
        entrenadorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entrenador", id.toString())).build();
    }

}
