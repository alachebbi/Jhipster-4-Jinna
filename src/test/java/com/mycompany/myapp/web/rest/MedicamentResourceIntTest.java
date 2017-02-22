package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Medicament;
import com.mycompany.myapp.repository.MedicamentRepository;

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
 * Test class for the MedicamentResource REST controller.
 *
 * @see MedicamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class MedicamentResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEVALIDITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEVALIDITE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEPRODUCTION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEPRODUCTION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMedicamentMockMvc;

    private Medicament medicament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MedicamentResource medicamentResource = new MedicamentResource(medicamentRepository);
        this.restMedicamentMockMvc = MockMvcBuilders.standaloneSetup(medicamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicament createEntity() {
        Medicament medicament = new Medicament()
                .nom(DEFAULT_NOM)
                .type(DEFAULT_TYPE)
                .quantity(DEFAULT_QUANTITY)
                .ref(DEFAULT_REF)
                .datevalidite(DEFAULT_DATEVALIDITE)
                .dateproduction(DEFAULT_DATEPRODUCTION);
        return medicament;
    }

    @Before
    public void initTest() {
        medicamentRepository.deleteAll();
        medicament = createEntity();
    }

    @Test
    public void createMedicament() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament

        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isCreated());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate + 1);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMedicament.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMedicament.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMedicament.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testMedicament.getDatevalidite()).isEqualTo(DEFAULT_DATEVALIDITE);
        assertThat(testMedicament.getDateproduction()).isEqualTo(DEFAULT_DATEPRODUCTION);
    }

    @Test
    public void createMedicamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament with an existing ID
        Medicament existingMedicament = new Medicament();
        existingMedicament.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMedicament)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMedicaments() throws Exception {
        // Initialize the database
        medicamentRepository.save(medicament);

        // Get all the medicamentList
        restMedicamentMockMvc.perform(get("/api/medicaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicament.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF.toString())))
            .andExpect(jsonPath("$.[*].datevalidite").value(hasItem(DEFAULT_DATEVALIDITE.toString())))
            .andExpect(jsonPath("$.[*].dateproduction").value(hasItem(DEFAULT_DATEPRODUCTION.toString())));
    }

    @Test
    public void getMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.save(medicament);

        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", medicament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicament.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF.toString()))
            .andExpect(jsonPath("$.datevalidite").value(DEFAULT_DATEVALIDITE.toString()))
            .andExpect(jsonPath("$.dateproduction").value(DEFAULT_DATEPRODUCTION.toString()));
    }

    @Test
    public void getNonExistingMedicament() throws Exception {
        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.save(medicament);
        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Update the medicament
        Medicament updatedMedicament = medicamentRepository.findOne(medicament.getId());
        updatedMedicament
                .nom(UPDATED_NOM)
                .type(UPDATED_TYPE)
                .quantity(UPDATED_QUANTITY)
                .ref(UPDATED_REF)
                .datevalidite(UPDATED_DATEVALIDITE)
                .dateproduction(UPDATED_DATEPRODUCTION);

        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicament)))
            .andExpect(status().isOk());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMedicament.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMedicament.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicament.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testMedicament.getDatevalidite()).isEqualTo(UPDATED_DATEVALIDITE);
        assertThat(testMedicament.getDateproduction()).isEqualTo(UPDATED_DATEPRODUCTION);
    }

    @Test
    public void updateNonExistingMedicament() throws Exception {
        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Create the Medicament

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isCreated());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.save(medicament);
        int databaseSizeBeforeDelete = medicamentRepository.findAll().size();

        // Get the medicament
        restMedicamentMockMvc.perform(delete("/api/medicaments/{id}", medicament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicament.class);
    }
}
