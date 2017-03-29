package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Traitement;
import com.mycompany.myapp.repository.TraitementRepository;

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
 * Test class for the TraitementResource REST controller.
 *
 * @see TraitementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class TraitementResourceIntTest {

    private static final String DEFAULT_MALADIE = "AAAAAAAAAA";
    private static final String UPDATED_MALADIE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMPTOMES = "AAAAAAAAAA";
    private static final String UPDATED_SYMPTOMES = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAMENTID = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAMENTID = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final String DEFAULT_FOIS = "AAAAAAAAAA";
    private static final String UPDATED_FOIS = "BBBBBBBBBB";

    @Autowired
    private TraitementRepository traitementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTraitementMockMvc;

    private Traitement traitement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            TraitementResource traitementResource = new TraitementResource(traitementRepository);
        this.restTraitementMockMvc = MockMvcBuilders.standaloneSetup(traitementResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Traitement createEntity() {
        Traitement traitement = new Traitement()
                .maladie(DEFAULT_MALADIE)
                .symptomes(DEFAULT_SYMPTOMES)
                .medicamentid(DEFAULT_MEDICAMENTID)
                .duree(DEFAULT_DUREE)
                .fois(DEFAULT_FOIS);
        return traitement;
    }

    @Before
    public void initTest() {
        traitementRepository.deleteAll();
        traitement = createEntity();
    }

    @Test
    public void createTraitement() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement

        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate + 1);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getMaladie()).isEqualTo(DEFAULT_MALADIE);
        assertThat(testTraitement.getSymptomes()).isEqualTo(DEFAULT_SYMPTOMES);
        assertThat(testTraitement.getMedicamentid()).isEqualTo(DEFAULT_MEDICAMENTID);
        assertThat(testTraitement.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testTraitement.getFois()).isEqualTo(DEFAULT_FOIS);
    }

    @Test
    public void createTraitementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = traitementRepository.findAll().size();

        // Create the Traitement with an existing ID
        Traitement existingTraitement = new Traitement();
        existingTraitement.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTraitementMockMvc.perform(post("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTraitement)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTraitements() throws Exception {
        // Initialize the database
        traitementRepository.save(traitement);

        // Get all the traitementList
        restTraitementMockMvc.perform(get("/api/traitements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(traitement.getId())))
            .andExpect(jsonPath("$.[*].maladie").value(hasItem(DEFAULT_MALADIE.toString())))
            .andExpect(jsonPath("$.[*].symptomes").value(hasItem(DEFAULT_SYMPTOMES.toString())))
            .andExpect(jsonPath("$.[*].medicamentid").value(hasItem(DEFAULT_MEDICAMENTID.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE.toString())))
            .andExpect(jsonPath("$.[*].fois").value(hasItem(DEFAULT_FOIS.toString())));
    }

    @Test
    public void getTraitement() throws Exception {
        // Initialize the database
        traitementRepository.save(traitement);

        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", traitement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(traitement.getId()))
            .andExpect(jsonPath("$.maladie").value(DEFAULT_MALADIE.toString()))
            .andExpect(jsonPath("$.symptomes").value(DEFAULT_SYMPTOMES.toString()))
            .andExpect(jsonPath("$.medicamentid").value(DEFAULT_MEDICAMENTID.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE.toString()))
            .andExpect(jsonPath("$.fois").value(DEFAULT_FOIS.toString()));
    }

    @Test
    public void getNonExistingTraitement() throws Exception {
        // Get the traitement
        restTraitementMockMvc.perform(get("/api/traitements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTraitement() throws Exception {
        // Initialize the database
        traitementRepository.save(traitement);
        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Update the traitement
        Traitement updatedTraitement = traitementRepository.findOne(traitement.getId());
        updatedTraitement
                .maladie(UPDATED_MALADIE)
                .symptomes(UPDATED_SYMPTOMES)
                .medicamentid(UPDATED_MEDICAMENTID)
                .duree(UPDATED_DUREE)
                .fois(UPDATED_FOIS);

        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTraitement)))
            .andExpect(status().isOk());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate);
        Traitement testTraitement = traitementList.get(traitementList.size() - 1);
        assertThat(testTraitement.getMaladie()).isEqualTo(UPDATED_MALADIE);
        assertThat(testTraitement.getSymptomes()).isEqualTo(UPDATED_SYMPTOMES);
        assertThat(testTraitement.getMedicamentid()).isEqualTo(UPDATED_MEDICAMENTID);
        assertThat(testTraitement.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testTraitement.getFois()).isEqualTo(UPDATED_FOIS);
    }

    @Test
    public void updateNonExistingTraitement() throws Exception {
        int databaseSizeBeforeUpdate = traitementRepository.findAll().size();

        // Create the Traitement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTraitementMockMvc.perform(put("/api/traitements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(traitement)))
            .andExpect(status().isCreated());

        // Validate the Traitement in the database
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTraitement() throws Exception {
        // Initialize the database
        traitementRepository.save(traitement);
        int databaseSizeBeforeDelete = traitementRepository.findAll().size();

        // Get the traitement
        restTraitementMockMvc.perform(delete("/api/traitements/{id}", traitement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Traitement> traitementList = traitementRepository.findAll();
        assertThat(traitementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traitement.class);
    }
}
