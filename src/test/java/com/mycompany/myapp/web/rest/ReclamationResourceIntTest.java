package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Reclamation;
import com.mycompany.myapp.repository.ReclamationRepository;

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
 * Test class for the ReclamationResource REST controller.
 *
 * @see ReclamationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class ReclamationResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_OBJET = "AAAAAAAAAA";
    private static final String UPDATED_OBJET = "BBBBBBBBBB";

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restReclamationMockMvc;

    private Reclamation reclamation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ReclamationResource reclamationResource = new ReclamationResource(reclamationRepository);
        this.restReclamationMockMvc = MockMvcBuilders.standaloneSetup(reclamationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reclamation createEntity() {
        Reclamation reclamation = new Reclamation()
                .titre(DEFAULT_TITRE)
                .objet(DEFAULT_OBJET);
        return reclamation;
    }

    @Before
    public void initTest() {
        reclamationRepository.deleteAll();
        reclamation = createEntity();
    }

    @Test
    public void createReclamation() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation

        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isCreated());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate + 1);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testReclamation.getObjet()).isEqualTo(DEFAULT_OBJET);
    }

    @Test
    public void createReclamationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reclamationRepository.findAll().size();

        // Create the Reclamation with an existing ID
        Reclamation existingReclamation = new Reclamation();
        existingReclamation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restReclamationMockMvc.perform(post("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingReclamation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllReclamations() throws Exception {
        // Initialize the database
        reclamationRepository.save(reclamation);

        // Get all the reclamationList
        restReclamationMockMvc.perform(get("/api/reclamations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reclamation.getId())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].objet").value(hasItem(DEFAULT_OBJET.toString())));
    }

    @Test
    public void getReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.save(reclamation);

        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", reclamation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reclamation.getId()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.objet").value(DEFAULT_OBJET.toString()));
    }

    @Test
    public void getNonExistingReclamation() throws Exception {
        // Get the reclamation
        restReclamationMockMvc.perform(get("/api/reclamations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.save(reclamation);
        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Update the reclamation
        Reclamation updatedReclamation = reclamationRepository.findOne(reclamation.getId());
        updatedReclamation
                .titre(UPDATED_TITRE)
                .objet(UPDATED_OBJET);

        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReclamation)))
            .andExpect(status().isOk());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate);
        Reclamation testReclamation = reclamationList.get(reclamationList.size() - 1);
        assertThat(testReclamation.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testReclamation.getObjet()).isEqualTo(UPDATED_OBJET);
    }

    @Test
    public void updateNonExistingReclamation() throws Exception {
        int databaseSizeBeforeUpdate = reclamationRepository.findAll().size();

        // Create the Reclamation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReclamationMockMvc.perform(put("/api/reclamations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reclamation)))
            .andExpect(status().isCreated());

        // Validate the Reclamation in the database
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteReclamation() throws Exception {
        // Initialize the database
        reclamationRepository.save(reclamation);
        int databaseSizeBeforeDelete = reclamationRepository.findAll().size();

        // Get the reclamation
        restReclamationMockMvc.perform(delete("/api/reclamations/{id}", reclamation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Reclamation> reclamationList = reclamationRepository.findAll();
        assertThat(reclamationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reclamation.class);
    }
}
