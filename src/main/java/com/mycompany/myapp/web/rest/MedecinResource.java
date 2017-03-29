package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Medecin;

import com.mycompany.myapp.domain.Medecin;

import com.mycompany.myapp.repository.MedecinRepository;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.awt.Color;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Medecin.
 */
@RestController
@RequestMapping("/api")
public class MedecinResource {

    private final Logger log = LoggerFactory.getLogger(MedecinResource.class);

    private static final String ENTITY_NAME = "medecin";

    private final MedecinRepository medecinRepository;

    public MedecinResource(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    /**
     * POST  /medecins : Create a new medecin.
     *
     * @param medecin the medecin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medecin, or with status 400 (Bad Request) if the medecin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medecins")
    @Timed
    public ResponseEntity<Medecin> createMedecin(@RequestBody Medecin medecin) throws URISyntaxException {
        log.debug("REST request to save Medecin : {}", medecin);
        if (medecin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medecin cannot already have an ID")).body(null);
        }
        Medecin result = medecinRepository.save(medecin);
        return ResponseEntity.created(new URI("/api/medecins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }




    /**
     * PUT  /medecins : Updates an existing medecin.
     *
     * @param medecin the medecin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medecin,
     * or with status 400 (Bad Request) if the medecin is not valid,
     * or with status 500 (Internal Server Error) if the medecin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medecins")
    @Timed
    public ResponseEntity<Medecin> updateMedecin(@RequestBody Medecin medecin) throws URISyntaxException {
        log.debug("REST request to update Medecin : {}", medecin);
        if (medecin.getId() == null) {
            return createMedecin(medecin);
        }
        Medecin result = medecinRepository.save(medecin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medecin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medecins : get all the medecins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medecins in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */


    @GetMapping("/medecins")
    @Timed
    public ResponseEntity<List<Medecin>> getAllMedecins(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Medecins");
        Page<Medecin> page = medecinRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medecins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medecins/:id : get the "id" medecin.
     *
     * @param id the id of the medecin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medecin, or with status 404 (Not Found)
     */
    @GetMapping("/medecins/{id}")
    @Timed
    public ResponseEntity<Medecin> getMedecin(@PathVariable String id) {
        log.debug("REST request to get Medecin : {}", id);
        Medecin medecin = medecinRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medecin));
    }

    /**
     * DELETE  /medecins/:id : delete the "id" medecin.
     *
     * @param id the id of the medecin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medecins/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedecin(@PathVariable String id) {
        log.debug("REST request to delete Medecin : {}", id);
        medecinRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
