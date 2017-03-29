package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Traitement;

import com.mycompany.myapp.repository.TraitementRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Traitement.
 */
@RestController
@RequestMapping("/api")
public class TraitementResource {

    private final Logger log = LoggerFactory.getLogger(TraitementResource.class);

    private static final String ENTITY_NAME = "traitement";
        
    private final TraitementRepository traitementRepository;

    public TraitementResource(TraitementRepository traitementRepository) {
        this.traitementRepository = traitementRepository;
    }

    /**
     * POST  /traitements : Create a new traitement.
     *
     * @param traitement the traitement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new traitement, or with status 400 (Bad Request) if the traitement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/traitements")
    @Timed
    public ResponseEntity<Traitement> createTraitement(@RequestBody Traitement traitement) throws URISyntaxException {
        log.debug("REST request to save Traitement : {}", traitement);
        if (traitement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new traitement cannot already have an ID")).body(null);
        }
        Traitement result = traitementRepository.save(traitement);
        return ResponseEntity.created(new URI("/api/traitements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /traitements : Updates an existing traitement.
     *
     * @param traitement the traitement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated traitement,
     * or with status 400 (Bad Request) if the traitement is not valid,
     * or with status 500 (Internal Server Error) if the traitement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/traitements")
    @Timed
    public ResponseEntity<Traitement> updateTraitement(@RequestBody Traitement traitement) throws URISyntaxException {
        log.debug("REST request to update Traitement : {}", traitement);
        if (traitement.getId() == null) {
            return createTraitement(traitement);
        }
        Traitement result = traitementRepository.save(traitement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, traitement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /traitements : get all the traitements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of traitements in body
     */
    @GetMapping("/traitements")
    @Timed
    public List<Traitement> getAllTraitements() {
        log.debug("REST request to get all Traitements");
        List<Traitement> traitements = traitementRepository.findAll();
        return traitements;
    }

    /**
     * GET  /traitements/:id : get the "id" traitement.
     *
     * @param id the id of the traitement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the traitement, or with status 404 (Not Found)
     */
    @GetMapping("/traitements/{id}")
    @Timed
    public ResponseEntity<Traitement> getTraitement(@PathVariable String id) {
        log.debug("REST request to get Traitement : {}", id);
        Traitement traitement = traitementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(traitement));
    }

    /**
     * DELETE  /traitements/:id : delete the "id" traitement.
     *
     * @param id the id of the traitement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/traitements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTraitement(@PathVariable String id) {
        log.debug("REST request to delete Traitement : {}", id);
        traitementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
