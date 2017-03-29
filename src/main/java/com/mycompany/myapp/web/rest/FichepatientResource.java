package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fichepatient;

import com.mycompany.myapp.repository.FichepatientRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fichepatient.
 */
@RestController
@RequestMapping("/api")
public class FichepatientResource {

    private final Logger log = LoggerFactory.getLogger(FichepatientResource.class);

    private static final String ENTITY_NAME = "fichepatient";
        
    private final FichepatientRepository fichepatientRepository;

    public FichepatientResource(FichepatientRepository fichepatientRepository) {
        this.fichepatientRepository = fichepatientRepository;
    }

    /**
     * POST  /fichepatients : Create a new fichepatient.
     *
     * @param fichepatient the fichepatient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichepatient, or with status 400 (Bad Request) if the fichepatient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichepatients")
    @Timed
    public ResponseEntity<Fichepatient> createFichepatient(@RequestBody Fichepatient fichepatient) throws URISyntaxException {
        log.debug("REST request to save Fichepatient : {}", fichepatient);
        if (fichepatient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fichepatient cannot already have an ID")).body(null);
        }
        Fichepatient result = fichepatientRepository.save(fichepatient);
        return ResponseEntity.created(new URI("/api/fichepatients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichepatients : Updates an existing fichepatient.
     *
     * @param fichepatient the fichepatient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichepatient,
     * or with status 400 (Bad Request) if the fichepatient is not valid,
     * or with status 500 (Internal Server Error) if the fichepatient couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichepatients")
    @Timed
    public ResponseEntity<Fichepatient> updateFichepatient(@RequestBody Fichepatient fichepatient) throws URISyntaxException {
        log.debug("REST request to update Fichepatient : {}", fichepatient);
        if (fichepatient.getId() == null) {
            return createFichepatient(fichepatient);
        }
        Fichepatient result = fichepatientRepository.save(fichepatient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichepatient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichepatients : get all the fichepatients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fichepatients in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fichepatients")
    @Timed
    public ResponseEntity<List<Fichepatient>> getAllFichepatients(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fichepatients");
        Page<Fichepatient> page = fichepatientRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichepatients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fichepatients/:id : get the "id" fichepatient.
     *
     * @param id the id of the fichepatient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichepatient, or with status 404 (Not Found)
     */
    @GetMapping("/fichepatients/{id}")
    @Timed
    public ResponseEntity<Fichepatient> getFichepatient(@PathVariable String id) {
        log.debug("REST request to get Fichepatient : {}", id);
        Fichepatient fichepatient = fichepatientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fichepatient));
    }

    /**
     * DELETE  /fichepatients/:id : delete the "id" fichepatient.
     *
     * @param id the id of the fichepatient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichepatients/{id}")
    @Timed
    public ResponseEntity<Void> deleteFichepatient(@PathVariable String id) {
        log.debug("REST request to delete Fichepatient : {}", id);
        fichepatientRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
