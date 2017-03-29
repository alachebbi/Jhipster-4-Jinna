package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Infirmier;


import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.service.MailService;
import com.mycompany.myapp.service.dto.UserDTO;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.repository.InfirmierRepository;

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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Infirmier.
 */
@RestController
@RequestMapping("/api")
public class InfirmierResource {

    private final Logger log = LoggerFactory.getLogger(InfirmierResource.class);

    private static final String ENTITY_NAME = "infirmier";

    private final InfirmierRepository infirmierRepository;

    private final UserService userService;

    private final MailService mailService;

    public InfirmierResource(InfirmierRepository infirmierRepository, UserService userService, MailService mailService) {
        this.infirmierRepository = infirmierRepository;
        this.userService = userService ;
        this.mailService = mailService;
    }


    /**
     * POST  /infirmiers : Create a new infirmier.
     *
     * @param infirmier the infirmier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new infirmier, or with status 400 (Bad Request) if the infirmier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/infirmiers")
    @Timed
    public ResponseEntity<Infirmier> createInfirmier(@RequestBody Infirmier infirmier) throws URISyntaxException {
        /*log.debug("REST request to save Infirmier : {}", infirmier);
        if (infirmier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new infirmier cannot already have an ID")).body(null);
        }
        Set<String> autrority = new HashSet<>();
       // autrority.add("ROLE_MEDECIN_CHEF");
        autrority.add("ROLE_ADMIN");
        UserDTO userDTO = new UserDTO(null, infirmier.getLogin(),infirmier.getNom(),infirmier.getPrenom(),"azse@htmail.fr",
         true,null, "en",null,null,null,null,autrority);
        User createUser = userService.createUser(userDTO);
        infirmier.setPassword(createUser.getPassword());
        Infirmier result = infirmierRepository.save(infirmier);
        mailService.sendCreationEmail(createUser);
        return ResponseEntity.created(new URI("/api/infirmiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }*/
        log.debug("REST request to save Medecin : {}", infirmier);
        if (infirmier.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medecin cannot already have an ID")).body(null);
        }
        Infirmier result = infirmierRepository.save(infirmier);
        return ResponseEntity.created(new URI("/api/infirmiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }











    /**
     * PUT  /infirmiers : Updates an existing infirmier.
     *
     * @param infirmier the infirmier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated infirmier,
     * or with status 400 (Bad Request) if the infirmier is not valid,
     * or with status 500 (Internal Server Error) if the infirmier couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/infirmiers")
    @Timed
    public ResponseEntity<Infirmier> updateInfirmier(@RequestBody Infirmier infirmier) throws URISyntaxException {
        log.debug("REST request to update Infirmier : {}", infirmier);
        if (infirmier.getId() == null) {
            return createInfirmier(infirmier);
        }
        Infirmier result = infirmierRepository.save(infirmier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, infirmier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /infirmiers : get all the infirmiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of infirmiers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/infirmiers")
    @Timed
    public ResponseEntity<List<Infirmier>> getAllInfirmiers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Infirmiers");
        Page<Infirmier> page = infirmierRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/infirmiers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /infirmiers/:id : get the "id" infirmier.
     *
     * @param id the id of the infirmier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the infirmier, or with status 404 (Not Found)
     */
    @GetMapping("/infirmiers/{id}")
    @Timed
    public ResponseEntity<Infirmier> getInfirmier(@PathVariable String id) {
        log.debug("REST request to get Infirmier : {}", id);
        Infirmier infirmier = infirmierRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(infirmier));
    }

    /**
     * DELETE  /infirmiers/:id : delete the "id" infirmier.
     *
     * @param id the id of the infirmier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/infirmiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteInfirmier(@PathVariable String id) {
        log.debug("REST request to delete Infirmier : {}", id);
        infirmierRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
