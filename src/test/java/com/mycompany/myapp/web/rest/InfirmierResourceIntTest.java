package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Infirmier;
import com.mycompany.myapp.repository.InfirmierRepository;
import com.mycompany.myapp.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InfirmierResource REST controller.
 *
 * @see InfirmierResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class InfirmierResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private InfirmierRepository infirmierRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInfirmierMockMvc;

    private Infirmier infirmier;

    @Before
    /*public void setup() {
        MockitoAnnotations.initMocks(this);
            InfirmierResource infirmierResource = new InfirmierResource(infirmierRepository);
        this.restInfirmierMockMvc = MockMvcBuilders.standaloneSetup(infirmierResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }*/

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infirmier createEntity() {
        Infirmier infirmier = new Infirmier()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .age(DEFAULT_AGE)
                .login(DEFAULT_LOGIN)
                .password(DEFAULT_PASSWORD);
        return infirmier;
    }

    @Before
    public void initTest() {
        infirmierRepository.deleteAll();
        infirmier = createEntity();
    }

    @Test
    public void createInfirmier() throws Exception {
        int databaseSizeBeforeCreate = infirmierRepository.findAll().size();

        // Create the Infirmier

        restInfirmierMockMvc.perform(post("/api/infirmiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infirmier)))
            .andExpect(status().isCreated());

        // Validate the Infirmier in the database
        List<Infirmier> infirmierList = infirmierRepository.findAll();
        assertThat(infirmierList).hasSize(databaseSizeBeforeCreate + 1);
        Infirmier testInfirmier = infirmierList.get(infirmierList.size() - 1);
        assertThat(testInfirmier.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testInfirmier.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testInfirmier.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testInfirmier.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testInfirmier.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    public void createInfirmierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infirmierRepository.findAll().size();

        // Create the Infirmier with an existing ID
        Infirmier existingInfirmier = new Infirmier();
        existingInfirmier.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfirmierMockMvc.perform(post("/api/infirmiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingInfirmier)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Infirmier> infirmierList = infirmierRepository.findAll();
        assertThat(infirmierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllInfirmiers() throws Exception {
        // Initialize the database
        infirmierRepository.save(infirmier);

        // Get all the infirmierList
        restInfirmierMockMvc.perform(get("/api/infirmiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infirmier.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    public void getInfirmier() throws Exception {
        // Initialize the database
        infirmierRepository.save(infirmier);

        // Get the infirmier
        restInfirmierMockMvc.perform(get("/api/infirmiers/{id}", infirmier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infirmier.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    public void getNonExistingInfirmier() throws Exception {
        // Get the infirmier
        restInfirmierMockMvc.perform(get("/api/infirmiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInfirmier() throws Exception {
        // Initialize the database
        infirmierRepository.save(infirmier);
        int databaseSizeBeforeUpdate = infirmierRepository.findAll().size();

        // Update the infirmier
        Infirmier updatedInfirmier = infirmierRepository.findOne(infirmier.getId());
        updatedInfirmier
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .age(UPDATED_AGE)
                .login(UPDATED_LOGIN)
                .password(UPDATED_PASSWORD);

        restInfirmierMockMvc.perform(put("/api/infirmiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfirmier)))
            .andExpect(status().isOk());

        // Validate the Infirmier in the database
        List<Infirmier> infirmierList = infirmierRepository.findAll();
        assertThat(infirmierList).hasSize(databaseSizeBeforeUpdate);
        Infirmier testInfirmier = infirmierList.get(infirmierList.size() - 1);
        assertThat(testInfirmier.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testInfirmier.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testInfirmier.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testInfirmier.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testInfirmier.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    public void updateNonExistingInfirmier() throws Exception {
        int databaseSizeBeforeUpdate = infirmierRepository.findAll().size();

        // Create the Infirmier

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInfirmierMockMvc.perform(put("/api/infirmiers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infirmier)))
            .andExpect(status().isCreated());

        // Validate the Infirmier in the database
        List<Infirmier> infirmierList = infirmierRepository.findAll();
        assertThat(infirmierList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteInfirmier() throws Exception {
        // Initialize the database
        infirmierRepository.save(infirmier);
        int databaseSizeBeforeDelete = infirmierRepository.findAll().size();

        // Get the infirmier
        restInfirmierMockMvc.perform(delete("/api/infirmiers/{id}", infirmier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Infirmier> infirmierList = infirmierRepository.findAll();
        assertThat(infirmierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infirmier.class);
    }
}
