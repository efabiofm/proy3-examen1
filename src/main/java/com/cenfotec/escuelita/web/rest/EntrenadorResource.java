package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.domain.Entrenador;

import com.cenfotec.escuelita.repository.EntrenadorRepository;
import com.cenfotec.escuelita.web.rest.util.HeaderUtil;

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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entrenador.
 */
@RestController
@RequestMapping("/api")
public class EntrenadorResource {

    private final Logger log = LoggerFactory.getLogger(EntrenadorResource.class);
        
    @Inject
    private EntrenadorRepository entrenadorRepository;

    /**
     * POST  /entrenadors : Create a new entrenador.
     *
     * @param entrenador the entrenador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrenador, or with status 400 (Bad Request) if the entrenador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrenadors")
    @Timed
    public ResponseEntity<Entrenador> createEntrenador(@Valid @RequestBody Entrenador entrenador) throws URISyntaxException {
        log.debug("REST request to save Entrenador : {}", entrenador);
        if (entrenador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entrenador", "idexists", "A new entrenador cannot already have an ID")).body(null);
        }
        Entrenador result = entrenadorRepository.save(entrenador);
        return ResponseEntity.created(new URI("/api/entrenadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entrenador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrenadors : Updates an existing entrenador.
     *
     * @param entrenador the entrenador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrenador,
     * or with status 400 (Bad Request) if the entrenador is not valid,
     * or with status 500 (Internal Server Error) if the entrenador couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrenadors")
    @Timed
    public ResponseEntity<Entrenador> updateEntrenador(@Valid @RequestBody Entrenador entrenador) throws URISyntaxException {
        log.debug("REST request to update Entrenador : {}", entrenador);
        if (entrenador.getId() == null) {
            return createEntrenador(entrenador);
        }
        Entrenador result = entrenadorRepository.save(entrenador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entrenador", entrenador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrenadors : get all the entrenadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entrenadors in body
     */
    @GetMapping("/entrenadors")
    @Timed
    public List<Entrenador> getAllEntrenadors() {
        log.debug("REST request to get all Entrenadors");
        List<Entrenador> entrenadors = entrenadorRepository.findAll();
        return entrenadors;
    }

    /**
     * GET  /entrenadors/:id : get the "id" entrenador.
     *
     * @param id the id of the entrenador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrenador, or with status 404 (Not Found)
     */
    @GetMapping("/entrenadors/{id}")
    @Timed
    public ResponseEntity<Entrenador> getEntrenador(@PathVariable Long id) {
        log.debug("REST request to get Entrenador : {}", id);
        Entrenador entrenador = entrenadorRepository.findOne(id);
        return Optional.ofNullable(entrenador)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entrenadors/:id : delete the "id" entrenador.
     *
     * @param id the id of the entrenador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrenadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrenador(@PathVariable Long id) {
        log.debug("REST request to delete Entrenador : {}", id);
        entrenadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entrenador", id.toString())).build();
    }

}
