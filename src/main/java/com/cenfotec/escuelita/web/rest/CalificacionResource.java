package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.domain.Calificacion;

import com.cenfotec.escuelita.repository.CalificacionRepository;
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
 * REST controller for managing Calificacion.
 */
@RestController
@RequestMapping("/api")
public class CalificacionResource {

    private final Logger log = LoggerFactory.getLogger(CalificacionResource.class);
        
    @Inject
    private CalificacionRepository calificacionRepository;

    /**
     * POST  /calificacions : Create a new calificacion.
     *
     * @param calificacion the calificacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calificacion, or with status 400 (Bad Request) if the calificacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calificacions")
    @Timed
    public ResponseEntity<Calificacion> createCalificacion(@Valid @RequestBody Calificacion calificacion) throws URISyntaxException {
        log.debug("REST request to save Calificacion : {}", calificacion);
        if (calificacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calificacion", "idexists", "A new calificacion cannot already have an ID")).body(null);
        }
        Calificacion result = calificacionRepository.save(calificacion);
        return ResponseEntity.created(new URI("/api/calificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calificacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calificacions : Updates an existing calificacion.
     *
     * @param calificacion the calificacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calificacion,
     * or with status 400 (Bad Request) if the calificacion is not valid,
     * or with status 500 (Internal Server Error) if the calificacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calificacions")
    @Timed
    public ResponseEntity<Calificacion> updateCalificacion(@Valid @RequestBody Calificacion calificacion) throws URISyntaxException {
        log.debug("REST request to update Calificacion : {}", calificacion);
        if (calificacion.getId() == null) {
            return createCalificacion(calificacion);
        }
        Calificacion result = calificacionRepository.save(calificacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calificacion", calificacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calificacions : get all the calificacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calificacions in body
     */
    @GetMapping("/calificacions")
    @Timed
    public List<Calificacion> getAllCalificacions() {
        log.debug("REST request to get all Calificacions");
        List<Calificacion> calificacions = calificacionRepository.findAll();
        return calificacions;
    }

    /**
     * GET  /calificacions/:id : get the "id" calificacion.
     *
     * @param id the id of the calificacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calificacion, or with status 404 (Not Found)
     */
    @GetMapping("/calificacions/{id}")
    @Timed
    public ResponseEntity<Calificacion> getCalificacion(@PathVariable Long id) {
        log.debug("REST request to get Calificacion : {}", id);
        Calificacion calificacion = calificacionRepository.findOne(id);
        return Optional.ofNullable(calificacion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calificacions/:id : delete the "id" calificacion.
     *
     * @param id the id of the calificacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calificacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Long id) {
        log.debug("REST request to delete Calificacion : {}", id);
        calificacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calificacion", id.toString())).build();
    }

}
