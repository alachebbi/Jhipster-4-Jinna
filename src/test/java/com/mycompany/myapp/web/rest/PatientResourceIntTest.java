package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Patient;
import com.mycompany.myapp.repository.PatientRepository;

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
import org.springframework.util.Base64Utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class PatientResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Long DEFAULT_CIN = 1L;
    private static final Long UPDATED_CIN = 2L;

    private static final LocalDate DEFAULT_DATEDENAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDENAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_NUMSECSOCIALE = 1L;
    private static final Long UPDATED_NUMSECSOCIALE = 2L;

    private static final byte[] DEFAULT_DOSSIER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOSSIER = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOSSIER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOSSIER_CONTENT_TYPE = "image/png";

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PatientResource patientResource = new PatientResource(patientRepository);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity() {
        Patient patient = new Patient()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .cin(DEFAULT_CIN)
                .datedenaissance(DEFAULT_DATEDENAISSANCE)
                .numsecsociale(DEFAULT_NUMSECSOCIALE)
                .dossier(DEFAULT_DOSSIER)
                .dossierContentType(DEFAULT_DOSSIER_CONTENT_TYPE);
        return patient;
    }

    @Before
    public void initTest() {
        patientRepository.deleteAll();
        patient = createEntity();
    }

    @Test
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPatient.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testPatient.getDatedenaissance()).isEqualTo(DEFAULT_DATEDENAISSANCE);
        assertThat(testPatient.getNumsecsociale()).isEqualTo(DEFAULT_NUMSECSOCIALE);
        assertThat(testPatient.getDossier()).isEqualTo(DEFAULT_DOSSIER);
        assertThat(testPatient.getDossierContentType()).isEqualTo(DEFAULT_DOSSIER_CONTENT_TYPE);
    }

    @Test
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        Patient existingPatient = new Patient();
        existingPatient.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPatient)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.save(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN.intValue())))
            .andExpect(jsonPath("$.[*].datedenaissance").value(hasItem(DEFAULT_DATEDENAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].numsecsociale").value(hasItem(DEFAULT_NUMSECSOCIALE.intValue())))
            .andExpect(jsonPath("$.[*].dossierContentType").value(hasItem(DEFAULT_DOSSIER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dossier").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOSSIER))));
    }

    @Test
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.save(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN.intValue()))
            .andExpect(jsonPath("$.datedenaissance").value(DEFAULT_DATEDENAISSANCE.toString()))
            .andExpect(jsonPath("$.numsecsociale").value(DEFAULT_NUMSECSOCIALE.intValue()))
            .andExpect(jsonPath("$.dossierContentType").value(DEFAULT_DOSSIER_CONTENT_TYPE))
            .andExpect(jsonPath("$.dossier").value(Base64Utils.encodeToString(DEFAULT_DOSSIER)));
    }

    @Test
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePatient() throws Exception {
        // Initialize the database
        patientRepository.save(patient);
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findOne(patient.getId());
        updatedPatient
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .cin(UPDATED_CIN)
                .datedenaissance(UPDATED_DATEDENAISSANCE)
                .numsecsociale(UPDATED_NUMSECSOCIALE)
                .dossier(UPDATED_DOSSIER)
                .dossierContentType(UPDATED_DOSSIER_CONTENT_TYPE);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testPatient.getDatedenaissance()).isEqualTo(UPDATED_DATEDENAISSANCE);
        assertThat(testPatient.getNumsecsociale()).isEqualTo(UPDATED_NUMSECSOCIALE);
        assertThat(testPatient.getDossier()).isEqualTo(UPDATED_DOSSIER);
        assertThat(testPatient.getDossierContentType()).isEqualTo(UPDATED_DOSSIER_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.save(patient);
        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Get the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
    }
}
