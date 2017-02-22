package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fichemedicale;

import com.mycompany.myapp.repository.FichemedicaleRepository;
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
 * REST controller for managing Fichemedicale.
 */
@RestController
@RequestMapping("/api")
public class FichemedicaleResource {

    private final Logger log = LoggerFactory.getLogger(FichemedicaleResource.class);

    private static final String ENTITY_NAME = "fichemedicale";
        
    private final FichemedicaleRepository fichemedicaleRepository;

    public FichemedicaleResource(FichemedicaleRepository fichemedicaleRepository) {
        this.fichemedicaleRepository = fichemedicaleRepository;
    }

    /**
     * POST  /fichemedicales : Create a new fichemedicale.
     *
     * @param fichemedicale the fichemedicale to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichemedicale, or with status 400 (Bad Request) if the fichemedicale has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichemedicales")
    @Timed
    public ResponseEntity<Fichemedicale> createFichemedicale(@RequestBody Fichemedicale fichemedicale) throws URISyntaxException {
        log.debug("REST request to save Fichemedicale : {}", fichemedicale);
        if (fichemedicale.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fichemedicale cannot already have an ID")).body(null);
        }
        Fichemedicale result = fichemedicaleRepository.save(fichemedicale);
        return ResponseEntity.created(new URI("/api/fichemedicales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichemedicales : Updates an existing fichemedicale.
     *
     * @param fichemedicale the fichemedicale to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichemedicale,
     * or with status 400 (Bad Request) if the fichemedicale is not valid,
     * or with status 500 (Internal Server Error) if the fichemedicale couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichemedicales")
    @Timed
    public ResponseEntity<Fichemedicale> updateFichemedicale(@RequestBody Fichemedicale fichemedicale) throws URISyntaxException {
        log.debug("REST request to update Fichemedicale : {}", fichemedicale);
        if (fichemedicale.getId() == null) {
            return createFichemedicale(fichemedicale);
        }
        Fichemedicale result = fichemedicaleRepository.save(fichemedicale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichemedicale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichemedicales : get all the fichemedicales.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fichemedicales in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fichemedicales")
    @Timed
    public ResponseEntity<List<Fichemedicale>> getAllFichemedicales(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fichemedicales");
        Page<Fichemedicale> page = fichemedicaleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichemedicales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fichemedicales/:id : get the "id" fichemedicale.
     *
     * @param id the id of the fichemedicale to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichemedicale, or with status 404 (Not Found)
     */
    @GetMapping("/fichemedicales/{id}")
    @Timed
    public ResponseEntity<Fichemedicale> getFichemedicale(@PathVariable String id) {
        log.debug("REST request to get Fichemedicale : {}", id);
        Fichemedicale fichemedicale = fichemedicaleRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fichemedicale));
    }

    /**
     * DELETE  /fichemedicales/:id : delete the "id" fichemedicale.
     *
     * @param id the id of the fichemedicale to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichemedicales/{id}")
    @Timed
    public ResponseEntity<Void> deleteFichemedicale(@PathVariable String id) {
        log.debug("REST request to delete Fichemedicale : {}", id);
        fichemedicaleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
