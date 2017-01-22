package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.domain.Posicion;

import com.cenfotec.escuelita.repository.PosicionRepository;
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
 * REST controller for managing Posicion.
 */
@RestController
@RequestMapping("/api")
public class PosicionResource {

    private final Logger log = LoggerFactory.getLogger(PosicionResource.class);
        
    @Inject
    private PosicionRepository posicionRepository;

    /**
     * POST  /posicions : Create a new posicion.
     *
     * @param posicion the posicion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posicion, or with status 400 (Bad Request) if the posicion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posicions")
    @Timed
    public ResponseEntity<Posicion> createPosicion(@Valid @RequestBody Posicion posicion) throws URISyntaxException {
        log.debug("REST request to save Posicion : {}", posicion);
        if (posicion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("posicion", "idexists", "A new posicion cannot already have an ID")).body(null);
        }
        Posicion result = posicionRepository.save(posicion);
        return ResponseEntity.created(new URI("/api/posicions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("posicion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posicions : Updates an existing posicion.
     *
     * @param posicion the posicion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated posicion,
     * or with status 400 (Bad Request) if the posicion is not valid,
     * or with status 500 (Internal Server Error) if the posicion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posicions")
    @Timed
    public ResponseEntity<Posicion> updatePosicion(@Valid @RequestBody Posicion posicion) throws URISyntaxException {
        log.debug("REST request to update Posicion : {}", posicion);
        if (posicion.getId() == null) {
            return createPosicion(posicion);
        }
        Posicion result = posicionRepository.save(posicion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("posicion", posicion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posicions : get all the posicions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posicions in body
     */
    @GetMapping("/posicions")
    @Timed
    public List<Posicion> getAllPosicions() {
        log.debug("REST request to get all Posicions");
        List<Posicion> posicions = posicionRepository.findAll();
        return posicions;
    }

    /**
     * GET  /posicions/:id : get the "id" posicion.
     *
     * @param id the id of the posicion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the posicion, or with status 404 (Not Found)
     */
    @GetMapping("/posicions/{id}")
    @Timed
    public ResponseEntity<Posicion> getPosicion(@PathVariable Long id) {
        log.debug("REST request to get Posicion : {}", id);
        Posicion posicion = posicionRepository.findOne(id);
        return Optional.ofNullable(posicion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /posicions/:id : delete the "id" posicion.
     *
     * @param id the id of the posicion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posicions/{id}")
    @Timed
    public ResponseEntity<Void> deletePosicion(@PathVariable Long id) {
        log.debug("REST request to delete Posicion : {}", id);
        posicionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("posicion", id.toString())).build();
    }

}
