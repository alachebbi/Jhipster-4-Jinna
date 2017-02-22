package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Servicemedical;

import com.mycompany.myapp.repository.ServicemedicalRepository;
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
 * REST controller for managing Servicemedical.
 */
@RestController
@RequestMapping("/api")
public class ServicemedicalResource {

    private final Logger log = LoggerFactory.getLogger(ServicemedicalResource.class);

    private static final String ENTITY_NAME = "servicemedical";
        
    private final ServicemedicalRepository servicemedicalRepository;

    public ServicemedicalResource(ServicemedicalRepository servicemedicalRepository) {
        this.servicemedicalRepository = servicemedicalRepository;
    }

    /**
     * POST  /servicemedicals : Create a new servicemedical.
     *
     * @param servicemedical the servicemedical to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicemedical, or with status 400 (Bad Request) if the servicemedical has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicemedicals")
    @Timed
    public ResponseEntity<Servicemedical> createServicemedical(@RequestBody Servicemedical servicemedical) throws URISyntaxException {
        log.debug("REST request to save Servicemedical : {}", servicemedical);
        if (servicemedical.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new servicemedical cannot already have an ID")).body(null);
        }
        Servicemedical result = servicemedicalRepository.save(servicemedical);
        return ResponseEntity.created(new URI("/api/servicemedicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicemedicals : Updates an existing servicemedical.
     *
     * @param servicemedical the servicemedical to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicemedical,
     * or with status 400 (Bad Request) if the servicemedical is not valid,
     * or with status 500 (Internal Server Error) if the servicemedical couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicemedicals")
    @Timed
    public ResponseEntity<Servicemedical> updateServicemedical(@RequestBody Servicemedical servicemedical) throws URISyntaxException {
        log.debug("REST request to update Servicemedical : {}", servicemedical);
        if (servicemedical.getId() == null) {
            return createServicemedical(servicemedical);
        }
        Servicemedical result = servicemedicalRepository.save(servicemedical);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicemedical.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicemedicals : get all the servicemedicals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of servicemedicals in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/servicemedicals")
    @Timed
    public ResponseEntity<List<Servicemedical>> getAllServicemedicals(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Servicemedicals");
        Page<Servicemedical> page = servicemedicalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servicemedicals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servicemedicals/:id : get the "id" servicemedical.
     *
     * @param id the id of the servicemedical to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicemedical, or with status 404 (Not Found)
     */
    @GetMapping("/servicemedicals/{id}")
    @Timed
    public ResponseEntity<Servicemedical> getServicemedical(@PathVariable String id) {
        log.debug("REST request to get Servicemedical : {}", id);
        Servicemedical servicemedical = servicemedicalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicemedical));
    }

    /**
     * DELETE  /servicemedicals/:id : delete the "id" servicemedical.
     *
     * @param id the id of the servicemedical to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicemedicals/{id}")
    @Timed
    public ResponseEntity<Void> deleteServicemedical(@PathVariable String id) {
        log.debug("REST request to delete Servicemedical : {}", id);
        servicemedicalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
