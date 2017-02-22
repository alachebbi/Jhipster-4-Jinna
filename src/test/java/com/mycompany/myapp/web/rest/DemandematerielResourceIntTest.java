package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Demandemateriel;
import com.mycompany.myapp.repository.DemandematerielRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DemandematerielResource REST controller.
 *
 * @see DemandematerielResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class DemandematerielResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

    @Autowired
    private DemandematerielRepository demandematerielRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDemandematerielMockMvc;

    private Demandemateriel demandemateriel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            DemandematerielResource demandematerielResource = new DemandematerielResource(demandematerielRepository);
        this.restDemandematerielMockMvc = MockMvcBuilders.standaloneSetup(demandematerielResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandemateriel createEntity() {
        Demandemateriel demandemateriel = new Demandemateriel()
                .date(DEFAULT_DATE)
                .time(DEFAULT_TIME)
                .type(DEFAULT_TYPE)
                .quantity(DEFAULT_QUANTITY)
                .etat(DEFAULT_ETAT);
        return demandemateriel;
    }

    @Before
    public void initTest() {
        demandematerielRepository.deleteAll();
        demandemateriel = createEntity();
    }

    @Test
    public void createDemandemateriel() throws Exception {
        int databaseSizeBeforeCreate = demandematerielRepository.findAll().size();

        // Create the Demandemateriel

        restDemandematerielMockMvc.perform(post("/api/demandemateriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandemateriel)))
            .andExpect(status().isCreated());

        // Validate the Demandemateriel in the database
        List<Demandemateriel> demandematerielList = demandematerielRepository.findAll();
        assertThat(demandematerielList).hasSize(databaseSizeBeforeCreate + 1);
        Demandemateriel testDemandemateriel = demandematerielList.get(demandematerielList.size() - 1);
        assertThat(testDemandemateriel.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDemandemateriel.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testDemandemateriel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDemandemateriel.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDemandemateriel.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    public void createDemandematerielWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandematerielRepository.findAll().size();

        // Create the Demandemateriel with an existing ID
        Demandemateriel existingDemandemateriel = new Demandemateriel();
        existingDemandemateriel.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandematerielMockMvc.perform(post("/api/demandemateriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDemandemateriel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Demandemateriel> demandematerielList = demandematerielRepository.findAll();
        assertThat(demandematerielList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDemandemateriels() throws Exception {
        // Initialize the database
        demandematerielRepository.save(demandemateriel);

        // Get all the demandematerielList
        restDemandematerielMockMvc.perform(get("/api/demandemateriels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandemateriel.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    public void getDemandemateriel() throws Exception {
        // Initialize the database
        demandematerielRepository.save(demandemateriel);

        // Get the demandemateriel
        restDemandematerielMockMvc.perform(get("/api/demandemateriels/{id}", demandemateriel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandemateriel.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    public void getNonExistingDemandemateriel() throws Exception {
        // Get the demandemateriel
        restDemandematerielMockMvc.perform(get("/api/demandemateriels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDemandemateriel() throws Exception {
        // Initialize the database
        demandematerielRepository.save(demandemateriel);
        int databaseSizeBeforeUpdate = demandematerielRepository.findAll().size();

        // Update the demandemateriel
        Demandemateriel updatedDemandemateriel = demandematerielRepository.findOne(demandemateriel.getId());
        updatedDemandemateriel
                .date(UPDATED_DATE)
                .time(UPDATED_TIME)
                .type(UPDATED_TYPE)
                .quantity(UPDATED_QUANTITY)
                .etat(UPDATED_ETAT);

        restDemandematerielMockMvc.perform(put("/api/demandemateriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandemateriel)))
            .andExpect(status().isOk());

        // Validate the Demandemateriel in the database
        List<Demandemateriel> demandematerielList = demandematerielRepository.findAll();
        assertThat(demandematerielList).hasSize(databaseSizeBeforeUpdate);
        Demandemateriel testDemandemateriel = demandematerielList.get(demandematerielList.size() - 1);
        assertThat(testDemandemateriel.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDemandemateriel.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testDemandemateriel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDemandemateriel.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDemandemateriel.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    public void updateNonExistingDemandemateriel() throws Exception {
        int databaseSizeBeforeUpdate = demandematerielRepository.findAll().size();

        // Create the Demandemateriel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDemandematerielMockMvc.perform(put("/api/demandemateriels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandemateriel)))
            .andExpect(status().isCreated());

        // Validate the Demandemateriel in the database
        List<Demandemateriel> demandematerielList = demandematerielRepository.findAll();
        assertThat(demandematerielList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDemandemateriel() throws Exception {
        // Initialize the database
        demandematerielRepository.save(demandemateriel);
        int databaseSizeBeforeDelete = demandematerielRepository.findAll().size();

        // Get the demandemateriel
        restDemandematerielMockMvc.perform(delete("/api/demandemateriels/{id}", demandemateriel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Demandemateriel> demandematerielList = demandematerielRepository.findAll();
        assertThat(demandematerielList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demandemateriel.class);
    }
}
