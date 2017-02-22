package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Materiel;

import com.mycompany.myapp.repository.MaterielRepository;
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
 * REST controller for managing Materiel.
 */
@RestController
@RequestMapping("/api")
public class MaterielResource {

    private final Logger log = LoggerFactory.getLogger(MaterielResource.class);

    private static final String ENTITY_NAME = "materiel";
        
    private final MaterielRepository materielRepository;

    public MaterielResource(MaterielRepository materielRepository) {
        this.materielRepository = materielRepository;
    }

    /**
     * POST  /materiels : Create a new materiel.
     *
     * @param materiel the materiel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new materiel, or with status 400 (Bad Request) if the materiel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/materiels")
    @Timed
    public ResponseEntity<Materiel> createMateriel(@RequestBody Materiel materiel) throws URISyntaxException {
        log.debug("REST request to save Materiel : {}", materiel);
        if (materiel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new materiel cannot already have an ID")).body(null);
        }
        Materiel result = materielRepository.save(materiel);
        return ResponseEntity.created(new URI("/api/materiels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /materiels : Updates an existing materiel.
     *
     * @param materiel the materiel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated materiel,
     * or with status 400 (Bad Request) if the materiel is not valid,
     * or with status 500 (Internal Server Error) if the materiel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/materiels")
    @Timed
    public ResponseEntity<Materiel> updateMateriel(@RequestBody Materiel materiel) throws URISyntaxException {
        log.debug("REST request to update Materiel : {}", materiel);
        if (materiel.getId() == null) {
            return createMateriel(materiel);
        }
        Materiel result = materielRepository.save(materiel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, materiel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /materiels : get all the materiels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of materiels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/materiels")
    @Timed
    public ResponseEntity<List<Materiel>> getAllMateriels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Materiels");
        Page<Materiel> page = materielRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/materiels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /materiels/:id : get the "id" materiel.
     *
     * @param id the id of the materiel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the materiel, or with status 404 (Not Found)
     */
    @GetMapping("/materiels/{id}")
    @Timed
    public ResponseEntity<Materiel> getMateriel(@PathVariable String id) {
        log.debug("REST request to get Materiel : {}", id);
        Materiel materiel = materielRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(materiel));
    }

    /**
     * DELETE  /materiels/:id : delete the "id" materiel.
     *
     * @param id the id of the materiel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/materiels/{id}")
    @Timed
    public ResponseEntity<Void> deleteMateriel(@PathVariable String id) {
        log.debug("REST request to delete Materiel : {}", id);
        materielRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
