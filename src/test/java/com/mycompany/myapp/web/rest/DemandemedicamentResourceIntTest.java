package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Demandemedicament;
import com.mycompany.myapp.repository.DemandemedicamentRepository;

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
 * Test class for the DemandemedicamentResource REST controller.
 *
 * @see DemandemedicamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class DemandemedicamentResourceIntTest {

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
    private DemandemedicamentRepository demandemedicamentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDemandemedicamentMockMvc;

    private Demandemedicament demandemedicament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            DemandemedicamentResource demandemedicamentResource = new DemandemedicamentResource(demandemedicamentRepository);
        this.restDemandemedicamentMockMvc = MockMvcBuilders.standaloneSetup(demandemedicamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demandemedicament createEntity() {
        Demandemedicament demandemedicament = new Demandemedicament()
                .date(DEFAULT_DATE)
                .time(DEFAULT_TIME)
                .type(DEFAULT_TYPE)
                .quantity(DEFAULT_QUANTITY)
                .etat(DEFAULT_ETAT);
        return demandemedicament;
    }

    @Before
    public void initTest() {
        demandemedicamentRepository.deleteAll();
        demandemedicament = createEntity();
    }

    @Test
    public void createDemandemedicament() throws Exception {
        int databaseSizeBeforeCreate = demandemedicamentRepository.findAll().size();

        // Create the Demandemedicament

        restDemandemedicamentMockMvc.perform(post("/api/demandemedicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandemedicament)))
            .andExpect(status().isCreated());

        // Validate the Demandemedicament in the database
        List<Demandemedicament> demandemedicamentList = demandemedicamentRepository.findAll();
        assertThat(demandemedicamentList).hasSize(databaseSizeBeforeCreate + 1);
        Demandemedicament testDemandemedicament = demandemedicamentList.get(demandemedicamentList.size() - 1);
        assertThat(testDemandemedicament.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDemandemedicament.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testDemandemedicament.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDemandemedicament.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDemandemedicament.getEtat()).isEqualTo(DEFAULT_ETAT);
    }

    @Test
    public void createDemandemedicamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = demandemedicamentRepository.findAll().size();

        // Create the Demandemedicament with an existing ID
        Demandemedicament existingDemandemedicament = new Demandemedicament();
        existingDemandemedicament.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandemedicamentMockMvc.perform(post("/api/demandemedicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDemandemedicament)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Demandemedicament> demandemedicamentList = demandemedicamentRepository.findAll();
        assertThat(demandemedicamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDemandemedicaments() throws Exception {
        // Initialize the database
        demandemedicamentRepository.save(demandemedicament);

        // Get all the demandemedicamentList
        restDemandemedicamentMockMvc.perform(get("/api/demandemedicaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandemedicament.getId())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    public void getDemandemedicament() throws Exception {
        // Initialize the database
        demandemedicamentRepository.save(demandemedicament);

        // Get the demandemedicament
        restDemandemedicamentMockMvc.perform(get("/api/demandemedicaments/{id}", demandemedicament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(demandemedicament.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    public void getNonExistingDemandemedicament() throws Exception {
        // Get the demandemedicament
        restDemandemedicamentMockMvc.perform(get("/api/demandemedicaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDemandemedicament() throws Exception {
        // Initialize the database
        demandemedicamentRepository.save(demandemedicament);
        int databaseSizeBeforeUpdate = demandemedicamentRepository.findAll().size();

        // Update the demandemedicament
        Demandemedicament updatedDemandemedicament = demandemedicamentRepository.findOne(demandemedicament.getId());
        updatedDemandemedicament
                .date(UPDATED_DATE)
                .time(UPDATED_TIME)
                .type(UPDATED_TYPE)
                .quantity(UPDATED_QUANTITY)
                .etat(UPDATED_ETAT);

        restDemandemedicamentMockMvc.perform(put("/api/demandemedicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDemandemedicament)))
            .andExpect(status().isOk());

        // Validate the Demandemedicament in the database
        List<Demandemedicament> demandemedicamentList = demandemedicamentRepository.findAll();
        assertThat(demandemedicamentList).hasSize(databaseSizeBeforeUpdate);
        Demandemedicament testDemandemedicament = demandemedicamentList.get(demandemedicamentList.size() - 1);
        assertThat(testDemandemedicament.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDemandemedicament.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testDemandemedicament.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDemandemedicament.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDemandemedicament.getEtat()).isEqualTo(UPDATED_ETAT);
    }

    @Test
    public void updateNonExistingDemandemedicament() throws Exception {
        int databaseSizeBeforeUpdate = demandemedicamentRepository.findAll().size();

        // Create the Demandemedicament

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDemandemedicamentMockMvc.perform(put("/api/demandemedicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(demandemedicament)))
            .andExpect(status().isCreated());

        // Validate the Demandemedicament in the database
        List<Demandemedicament> demandemedicamentList = demandemedicamentRepository.findAll();
        assertThat(demandemedicamentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDemandemedicament() throws Exception {
        // Initialize the database
        demandemedicamentRepository.save(demandemedicament);
        int databaseSizeBeforeDelete = demandemedicamentRepository.findAll().size();

        // Get the demandemedicament
        restDemandemedicamentMockMvc.perform(delete("/api/demandemedicaments/{id}", demandemedicament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Demandemedicament> demandemedicamentList = demandemedicamentRepository.findAll();
        assertThat(demandemedicamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demandemedicament.class);
    }
}
