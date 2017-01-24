package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.service.CalificacionService;
import com.cenfotec.escuelita.web.rest.util.HeaderUtil;
import com.cenfotec.escuelita.service.dto.CalificacionDTO;

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
 * REST controller for managing Calificacion.
 */
@RestController
@RequestMapping("/api")
public class CalificacionResource {

    private final Logger log = LoggerFactory.getLogger(CalificacionResource.class);

    @Inject
    private CalificacionService calificacionService;

    /**
     * POST  /calificacions : Create a new calificacion.
     *
     * @param calificacionDTO the calificacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new calificacionDTO, or with status 400 (Bad Request) if the calificacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/calificacions")
    @Timed
    public ResponseEntity<CalificacionDTO> createCalificacion(@Valid @RequestBody CalificacionDTO calificacionDTO) throws URISyntaxException {
        log.debug("REST request to save Calificacion : {}", calificacionDTO);
        if (calificacionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("calificacion", "idexists", "A new calificacion cannot already have an ID")).body(null);
        }
        CalificacionDTO result = calificacionService.save(calificacionDTO);
        return ResponseEntity.created(new URI("/api/calificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("calificacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /calificacions : Updates an existing calificacion.
     *
     * @param calificacionDTO the calificacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated calificacionDTO,
     * or with status 400 (Bad Request) if the calificacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the calificacionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/calificacions")
    @Timed
    public ResponseEntity<CalificacionDTO> updateCalificacion(@Valid @RequestBody CalificacionDTO calificacionDTO) throws URISyntaxException {
        log.debug("REST request to update Calificacion : {}", calificacionDTO);
        if (calificacionDTO.getId() == null) {
            return createCalificacion(calificacionDTO);
        }
        CalificacionDTO result = calificacionService.save(calificacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("calificacion", calificacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /calificacions : get all the calificacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of calificacions in body
     */
    @GetMapping("/calificacions")
    @Timed
    public List<CalificacionDTO> getAllCalificacions() {
        log.debug("REST request to get all Calificacions");
        return calificacionService.findAll();
    }

    /**
     * GET  /calificacions/:id : get the "id" calificacion.
     *
     * @param id the id of the calificacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the calificacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/calificacions/{id}")
    @Timed
    public ResponseEntity<CalificacionDTO> getCalificacion(@PathVariable Long id) {
        log.debug("REST request to get Calificacion : {}", id);
        CalificacionDTO calificacionDTO = calificacionService.findOne(id);
        return Optional.ofNullable(calificacionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /calificacions/:id : delete the "id" calificacion.
     *
     * @param id the id of the calificacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/calificacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteCalificacion(@PathVariable Long id) {
        log.debug("REST request to delete Calificacion : {}", id);
        calificacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("calificacion", id.toString())).build();
    }

}
