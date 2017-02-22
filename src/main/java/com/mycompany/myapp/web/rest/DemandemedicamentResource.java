package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Demandemedicament;

import com.mycompany.myapp.repository.DemandemedicamentRepository;
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
 * REST controller for managing Demandemedicament.
 */
@RestController
@RequestMapping("/api")
public class DemandemedicamentResource {

    private final Logger log = LoggerFactory.getLogger(DemandemedicamentResource.class);

    private static final String ENTITY_NAME = "demandemedicament";
        
    private final DemandemedicamentRepository demandemedicamentRepository;

    public DemandemedicamentResource(DemandemedicamentRepository demandemedicamentRepository) {
        this.demandemedicamentRepository = demandemedicamentRepository;
    }

    /**
     * POST  /demandemedicaments : Create a new demandemedicament.
     *
     * @param demandemedicament the demandemedicament to create
     * @return the ResponseEntity with status 201 (Created) and with body the new demandemedicament, or with status 400 (Bad Request) if the demandemedicament has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/demandemedicaments")
    @Timed
    public ResponseEntity<Demandemedicament> createDemandemedicament(@RequestBody Demandemedicament demandemedicament) throws URISyntaxException {
        log.debug("REST request to save Demandemedicament : {}", demandemedicament);
        if (demandemedicament.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new demandemedicament cannot already have an ID")).body(null);
        }
        Demandemedicament result = demandemedicamentRepository.save(demandemedicament);
        return ResponseEntity.created(new URI("/api/demandemedicaments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /demandemedicaments : Updates an existing demandemedicament.
     *
     * @param demandemedicament the demandemedicament to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated demandemedicament,
     * or with status 400 (Bad Request) if the demandemedicament is not valid,
     * or with status 500 (Internal Server Error) if the demandemedicament couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/demandemedicaments")
    @Timed
    public ResponseEntity<Demandemedicament> updateDemandemedicament(@RequestBody Demandemedicament demandemedicament) throws URISyntaxException {
        log.debug("REST request to update Demandemedicament : {}", demandemedicament);
        if (demandemedicament.getId() == null) {
            return createDemandemedicament(demandemedicament);
        }
        Demandemedicament result = demandemedicamentRepository.save(demandemedicament);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, demandemedicament.getId().toString()))
            .body(result);
    }

    /**
     * GET  /demandemedicaments : get all the demandemedicaments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of demandemedicaments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/demandemedicaments")
    @Timed
    public ResponseEntity<List<Demandemedicament>> getAllDemandemedicaments(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Demandemedicaments");
        Page<Demandemedicament> page = demandemedicamentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/demandemedicaments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /demandemedicaments/:id : get the "id" demandemedicament.
     *
     * @param id the id of the demandemedicament to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the demandemedicament, or with status 404 (Not Found)
     */
    @GetMapping("/demandemedicaments/{id}")
    @Timed
    public ResponseEntity<Demandemedicament> getDemandemedicament(@PathVariable String id) {
        log.debug("REST request to get Demandemedicament : {}", id);
        Demandemedicament demandemedicament = demandemedicamentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(demandemedicament));
    }

    /**
     * DELETE  /demandemedicaments/:id : delete the "id" demandemedicament.
     *
     * @param id the id of the demandemedicament to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/demandemedicaments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDemandemedicament(@PathVariable String id) {
        log.debug("REST request to delete Demandemedicament : {}", id);
        demandemedicamentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
