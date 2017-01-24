package com.cenfotec.escuelita.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cenfotec.escuelita.service.PosicionService;
import com.cenfotec.escuelita.web.rest.util.HeaderUtil;
import com.cenfotec.escuelita.service.dto.PosicionDTO;

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
 * REST controller for managing Posicion.
 */
@RestController
@RequestMapping("/api")
public class PosicionResource {

    private final Logger log = LoggerFactory.getLogger(PosicionResource.class);
        
    @Inject
    private PosicionService posicionService;

    /**
     * POST  /posicions : Create a new posicion.
     *
     * @param posicionDTO the posicionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new posicionDTO, or with status 400 (Bad Request) if the posicion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posicions")
    @Timed
    public ResponseEntity<PosicionDTO> createPosicion(@Valid @RequestBody PosicionDTO posicionDTO) throws URISyntaxException {
        log.debug("REST request to save Posicion : {}", posicionDTO);
        if (posicionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("posicion", "idexists", "A new posicion cannot already have an ID")).body(null);
        }
        PosicionDTO result = posicionService.save(posicionDTO);
        return ResponseEntity.created(new URI("/api/posicions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("posicion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posicions : Updates an existing posicion.
     *
     * @param posicionDTO the posicionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated posicionDTO,
     * or with status 400 (Bad Request) if the posicionDTO is not valid,
     * or with status 500 (Internal Server Error) if the posicionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posicions")
    @Timed
    public ResponseEntity<PosicionDTO> updatePosicion(@Valid @RequestBody PosicionDTO posicionDTO) throws URISyntaxException {
        log.debug("REST request to update Posicion : {}", posicionDTO);
        if (posicionDTO.getId() == null) {
            return createPosicion(posicionDTO);
        }
        PosicionDTO result = posicionService.save(posicionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("posicion", posicionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posicions : get all the posicions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of posicions in body
     */
    @GetMapping("/posicions")
    @Timed
    public List<PosicionDTO> getAllPosicions() {
        log.debug("REST request to get all Posicions");
        return posicionService.findAll();
    }

    /**
     * GET  /posicions/:id : get the "id" posicion.
     *
     * @param id the id of the posicionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the posicionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/posicions/{id}")
    @Timed
    public ResponseEntity<PosicionDTO> getPosicion(@PathVariable Long id) {
        log.debug("REST request to get Posicion : {}", id);
        PosicionDTO posicionDTO = posicionService.findOne(id);
        return Optional.ofNullable(posicionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /posicions/:id : delete the "id" posicion.
     *
     * @param id the id of the posicionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posicions/{id}")
    @Timed
    public ResponseEntity<Void> deletePosicion(@PathVariable Long id) {
        log.debug("REST request to delete Posicion : {}", id);
        posicionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("posicion", id.toString())).build();
    }

}
