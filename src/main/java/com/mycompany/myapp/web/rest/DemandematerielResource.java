package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Demandemateriel;

import com.mycompany.myapp.repository.DemandematerielRepository;
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
 * REST controller for managing Demandemateriel.
 */
@RestController
@RequestMapping("/api")
public class DemandematerielResource {

    private final Logger log = LoggerFactory.getLogger(DemandematerielResource.class);

    private static final String ENTITY_NAME = "demandemateriel";
        
    private final DemandematerielRepository demandematerielRepository;

    public DemandematerielResource(DemandematerielRepository demandematerielRepository) {
        this.demandematerielRepository = demandematerielRepository;
    }

    /**
     * POST  /demandemateriels : Create a new demandemateriel.
     *
     * @param demandemateriel the demandemateriel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandemateriel, or with status 400 (Bad Request) if the demandemateriel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demandemateriels")
    @Timed
    public ResponseEntity<Demandemateriel> createDemandemateriel(@RequestBody Demandemateriel demandemateriel) throws URISyntaxException {
        log.debug("REST request to save Demandemateriel : {}", demandemateriel);
        if (demandemateriel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new demandemateriel cannot already have an ID")).body(null);
        }
        Demandemateriel result = demandematerielRepository.save(demandemateriel);
        return ResponseEntity.created(new URI("/api/demandemateriels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demandemateriels : Updates an existing demandemateriel.
     *
     * @param demandemateriel the demandemateriel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandemateriel,
     * or with status 400 (Bad Request) if the demandemateriel is not valid,
     * or with status 500 (Internal Server Error) if the demandemateriel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demandemateriels")
    @Timed
    public ResponseEntity<Demandemateriel> updateDemandemateriel(@RequestBody Demandemateriel demandemateriel) throws URISyntaxException {
        log.debug("REST request to update Demandemateriel : {}", demandemateriel);
        if (demandemateriel.getId() == null) {
            return createDemandemateriel(demandemateriel);
        }
        Demandemateriel result = demandematerielRepository.save(demandemateriel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandemateriel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demandemateriels : get all the demandemateriels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of demandemateriels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/demandemateriels")
    @Timed
    public ResponseEntity<List<Demandemateriel>> getAllDemandemateriels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Demandemateriels");
        Page<Demandemateriel> page = demandematerielRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/demandemateriels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /demandemateriels/:id : get the "id" demandemateriel.
     *
     * @param id the id of the demandemateriel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandemateriel, or with status 404 (Not Found)
     */
    @GetMapping("/demandemateriels/{id}")
    @Timed
    public ResponseEntity<Demandemateriel> getDemandemateriel(@PathVariable String id) {
        log.debug("REST request to get Demandemateriel : {}", id);
        Demandemateriel demandemateriel = demandematerielRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(demandemateriel));
    }

    /**
     * DELETE  /demandemateriels/:id : delete the "id" demandemateriel.
     *
     * @param id the id of the demandemateriel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demandemateriels/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemandemateriel(@PathVariable String id) {
        log.debug("REST request to delete Demandemateriel : {}", id);
        demandematerielRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
