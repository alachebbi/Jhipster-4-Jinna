package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Reclamation;

import com.mycompany.myapp.repository.ReclamationRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Reclamation.
 */
@RestController
@RequestMapping("/api")
public class ReclamationResource {

    private final Logger log = LoggerFactory.getLogger(ReclamationResource.class);

    private static final String ENTITY_NAME = "reclamation";

    private final ReclamationRepository reclamationRepository;

    public ReclamationResource(ReclamationRepository reclamationRepository) {
        this.reclamationRepository = reclamationRepository;
    }

    /**
     * POST  /reclamations : Create a new reclamation.
     *
     * @param reclamation the reclamation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reclamation, or with status 400 (Bad Request) if the reclamation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reclamations")
    @Timed
    //@Secured(AuthoritiesConstants.MEDECIN)
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) throws URISyntaxException {
        log.debug("REST request to save Reclamation : {}", reclamation);
        if (reclamation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reclamation cannot already have an ID")).body(null);
        }
        Reclamation result = reclamationRepository.save(reclamation);
        return ResponseEntity.created(new URI("/api/reclamations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reclamations : Updates an existing reclamation.
     *
     * @param reclamation the reclamation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reclamation,
     * or with status 400 (Bad Request) if the reclamation is not valid,
     * or with status 500 (Internal Server Error) if the reclamation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reclamations")
    @Timed
   // @Secured(AuthoritiesConstants.MEDECIN)
    public ResponseEntity<Reclamation> updateReclamation(@RequestBody Reclamation reclamation) throws URISyntaxException {
        log.debug("REST request to update Reclamation : {}", reclamation);
        if (reclamation.getId() == null) {
            return createReclamation(reclamation);
        }
        Reclamation result = reclamationRepository.save(reclamation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reclamation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reclamations : get all the reclamations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of reclamations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/reclamations")
    @Timed
 //   @Secured(AuthoritiesConstants.MEDECIN)
    public ResponseEntity<List<Reclamation>> getAllReclamations(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Reclamations");
        Page<Reclamation> page = reclamationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reclamations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /reclamations/:id : get the "id" reclamation.
     *
     * @param id the id of the reclamation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reclamation, or with status 404 (Not Found)
     */
    @GetMapping("/reclamations/{id}")
    @Timed
 //   @Secured(AuthoritiesConstants.MEDECIN)
    public ResponseEntity<Reclamation> getReclamation(@PathVariable String id) {
        log.debug("REST request to get Reclamation : {}", id);
        Reclamation reclamation = reclamationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reclamation));
    }

    /**
     * DELETE  /reclamations/:id : delete the "id" reclamation.
     *
     * @param id the id of the reclamation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reclamations/{id}")
    @Timed
  //  @Secured(AuthoritiesConstants.MEDECIN)
    public ResponseEntity<Void> deleteReclamation(@PathVariable String id) {
        log.debug("REST request to delete Reclamation : {}", id);
        reclamationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
