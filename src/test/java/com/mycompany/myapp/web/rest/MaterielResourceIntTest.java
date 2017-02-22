package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Materiel;
import com.mycompany.myapp.repository.MaterielRepository;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaterielResource REST controller.
 *
 * @see MaterielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class MaterielResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEPRODUCTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEPRODUCTION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEVALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEVALIDITE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MaterielRepository materielRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMaterielMockMvc;

    private Materiel materiel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MaterielResource materielResource = new MaterielResource(materielRepository);
        this.restMaterielMockMvc = MockMvcBuilders.standaloneSetup(materielResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materiel createEntity() {
        Materiel materiel = new Materiel()
                .nom(DEFAULT_NOM)
                .type(DEFAULT_TYPE)
                .quantity(DEFAULT_QUANTITY)
                .ref(DEFAULT_REF)
                .dateproduction(DEFAULT_DATEPRODUCTION)
                .datevalidite(DEFAULT_DATEVALIDITE);
        return materiel;
    }

    @Before
    public void initTest() {
        materielRepository.deleteAll();
        materiel = createEntity();
    }

    @Test
    public void createMateriel() throws Exception {
        int databaseSizeBeforeCreate = materielRepository.findAll().size();

        // Create the Materiel

        restMaterielMockMvc.perform(post("/api/materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isCreated());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate + 1);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMateriel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMateriel.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMateriel.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testMateriel.getDateproduction()).isEqualTo(DEFAULT_DATEPRODUCTION);
        assertThat(testMateriel.getDatevalidite()).isEqualTo(DEFAULT_DATEVALIDITE);
    }

    @Test
    public void createMaterielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materielRepository.findAll().size();

        // Create the Materiel with an existing ID
        Materiel existingMateriel = new Materiel();
        existingMateriel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterielMockMvc.perform(post("/api/materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMateriel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMateriels() throws Exception {
        // Initialize the database
        materielRepository.save(materiel);

        // Get all the materielList
        restMaterielMockMvc.perform(get("/api/materiels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiel.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].dateproduction").value(hasItem(DEFAULT_DATEPRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].datevalidite").value(hasItem(DEFAULT_DATEVALIDITE.toString())));
    }

    @Test
    public void getMateriel() throws Exception {
        // Initialize the database
        materielRepository.save(materiel);

        // Get the materiel
        restMaterielMockMvc.perform(get("/api/materiels/{id}", materiel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materiel.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.dateproduction").value(DEFAULT_DATEPRODUCTION.toString()))
            .andExpect(jsonPath("$.datevalidite").value(DEFAULT_DATEVALIDITE.toString()));
    }

    @Test
    public void getNonExistingMateriel() throws Exception {
        // Get the materiel
        restMaterielMockMvc.perform(get("/api/materiels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMateriel() throws Exception {
        // Initialize the database
        materielRepository.save(materiel);
        int databaseSizeBeforeUpdate = materielRepository.findAll().size();

        // Update the materiel
        Materiel updatedMateriel = materielRepository.findOne(materiel.getId());
        updatedMateriel
                .nom(UPDATED_NOM)
                .type(UPDATED_TYPE)
                .quantity(UPDATED_QUANTITY)
                .ref(UPDATED_REF)
                .dateproduction(UPDATED_DATEPRODUCTION)
                .datevalidite(UPDATED_DATEVALIDITE);

        restMaterielMockMvc.perform(put("/api/materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMateriel)))
            .andExpect(status().isOk());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate);
        Materiel testMateriel = materielList.get(materielList.size() - 1);
        assertThat(testMateriel.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMateriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMateriel.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMateriel.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testMateriel.getDateproduction()).isEqualTo(UPDATED_DATEPRODUCTION);
        assertThat(testMateriel.getDatevalidite()).isEqualTo(UPDATED_DATEVALIDITE);
    }

    @Test
    public void updateNonExistingMateriel() throws Exception {
        int databaseSizeBeforeUpdate = materielRepository.findAll().size();

        // Create the Materiel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMaterielMockMvc.perform(put("/api/materiels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materiel)))
            .andExpect(status().isCreated());

        // Validate the Materiel in the database
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMateriel() throws Exception {
        // Initialize the database
        materielRepository.save(materiel);
        int databaseSizeBeforeDelete = materielRepository.findAll().size();

        // Get the materiel
        restMaterielMockMvc.perform(delete("/api/materiels/{id}", materiel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Materiel> materielList = materielRepository.findAll();
        assertThat(materielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materiel.class);
    }
}
