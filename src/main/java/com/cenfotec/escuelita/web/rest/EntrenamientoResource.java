package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.domain.Entrenamiento;

import com.cenfotec.escuelita.repository.EntrenamientoRepository;
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
 * REST controller for managing Entrenamiento.
 */
@RestController
@RequestMapping("/api")
public class EntrenamientoResource {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoResource.class);
        
    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    /**
     * POST  /entrenamientos : Create a new entrenamiento.
     *
     * @param entrenamiento the entrenamiento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrenamiento, or with status 400 (Bad Request) if the entrenamiento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrenamientos")
    @Timed
    public ResponseEntity<Entrenamiento> createEntrenamiento(@Valid @RequestBody Entrenamiento entrenamiento) throws URISyntaxException {
        log.debug("REST request to save Entrenamiento : {}", entrenamiento);
        if (entrenamiento.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entrenamiento", "idexists", "A new entrenamiento cannot already have an ID")).body(null);
        }
        Entrenamiento result = entrenamientoRepository.save(entrenamiento);
        return ResponseEntity.created(new URI("/api/entrenamientos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entrenamiento", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrenamientos : Updates an existing entrenamiento.
     *
     * @param entrenamiento the entrenamiento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrenamiento,
     * or with status 400 (Bad Request) if the entrenamiento is not valid,
     * or with status 500 (Internal Server Error) if the entrenamiento couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrenamientos")
    @Timed
    public ResponseEntity<Entrenamiento> updateEntrenamiento(@Valid @RequestBody Entrenamiento entrenamiento) throws URISyntaxException {
        log.debug("REST request to update Entrenamiento : {}", entrenamiento);
        if (entrenamiento.getId() == null) {
            return createEntrenamiento(entrenamiento);
        }
        Entrenamiento result = entrenamientoRepository.save(entrenamiento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entrenamiento", entrenamiento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrenamientos : get all the entrenamientos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entrenamientos in body
     */
    @GetMapping("/entrenamientos")
    @Timed
    public List<Entrenamiento> getAllEntrenamientos() {
        log.debug("REST request to get all Entrenamientos");
        List<Entrenamiento> entrenamientos = entrenamientoRepository.findAll();
        return entrenamientos;
    }

    /**
     * GET  /entrenamientos/:id : get the "id" entrenamiento.
     *
     * @param id the id of the entrenamiento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrenamiento, or with status 404 (Not Found)
     */
    @GetMapping("/entrenamientos/{id}")
    @Timed
    public ResponseEntity<Entrenamiento> getEntrenamiento(@PathVariable Long id) {
        log.debug("REST request to get Entrenamiento : {}", id);
        Entrenamiento entrenamiento = entrenamientoRepository.findOne(id);
        return Optional.ofNullable(entrenamiento)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entrenamientos/:id : delete the "id" entrenamiento.
     *
     * @param id the id of the entrenamiento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrenamientos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrenamiento(@PathVariable Long id) {
        log.debug("REST request to delete Entrenamiento : {}", id);
        entrenamientoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entrenamiento", id.toString())).build();
    }

}
