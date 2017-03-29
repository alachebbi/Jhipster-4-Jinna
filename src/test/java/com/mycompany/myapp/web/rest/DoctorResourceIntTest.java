package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.repository.DoctorRepository;

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
 * Test class for the DoctorResource REST controller.
 *
 * @see DoctorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class DoctorResourceIntTest {

    private static final String DEFAULT_MOTDEPASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOTDEPASSE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICEMEDI = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEMEDI = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOMETPRENOM = "AAAAAAAAAA";
    private static final String UPDATED_NOMETPRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATENAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            DoctorResource doctorResource = new DoctorResource(doctorRepository);
        this.restDoctorMockMvc = MockMvcBuilders.standaloneSetup(doctorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity() {
        Doctor doctor = new Doctor()
                .motdepasse(DEFAULT_MOTDEPASSE)
                .login(DEFAULT_LOGIN)
                .servicemedi(DEFAULT_SERVICEMEDI)
                .photo(DEFAULT_PHOTO)
                .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
                .specialite(DEFAULT_SPECIALITE)
                .nometprenom(DEFAULT_NOMETPRENOM)
                .datenaissance(DEFAULT_DATENAISSANCE)
                .daten(DEFAULT_DATEN);
        return doctor;
    }

    @Before
    public void initTest() {
        doctorRepository.deleteAll();
        doctor = createEntity();
    }

    @Test
    public void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor

        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctor)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getMotdepasse()).isEqualTo(DEFAULT_MOTDEPASSE);
        assertThat(testDoctor.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testDoctor.getServicemedi()).isEqualTo(DEFAULT_SERVICEMEDI);
        assertThat(testDoctor.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDoctor.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testDoctor.getSpecialite()).isEqualTo(DEFAULT_SPECIALITE);
        assertThat(testDoctor.getNometprenom()).isEqualTo(DEFAULT_NOMETPRENOM);
        assertThat(testDoctor.getDatenaissance()).isEqualTo(DEFAULT_DATENAISSANCE);
        assertThat(testDoctor.getDaten()).isEqualTo(DEFAULT_DATEN);
    }

    @Test
    public void createDoctorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor with an existing ID
        Doctor existingDoctor = new Doctor();
        existingDoctor.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc.perform(post("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingDoctor)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.save(doctor);

        // Get all the doctorList
        restDoctorMockMvc.perform(get("/api/doctors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId())))
            .andExpect(jsonPath("$.[*].motdepasse").value(hasItem(DEFAULT_MOTDEPASSE.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].servicemedi").value(hasItem(DEFAULT_SERVICEMEDI.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())))
            .andExpect(jsonPath("$.[*].nometprenom").value(hasItem(DEFAULT_NOMETPRENOM.toString())))
            .andExpect(jsonPath("$.[*].datenaissance").value(hasItem(DEFAULT_DATENAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].daten").value(hasItem(DEFAULT_DATEN.toString())));
    }

    @Test
    public void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.save(doctor);

        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId()))
            .andExpect(jsonPath("$.motdepasse").value(DEFAULT_MOTDEPASSE.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.servicemedi").value(DEFAULT_SERVICEMEDI.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()))
            .andExpect(jsonPath("$.nometprenom").value(DEFAULT_NOMETPRENOM.toString()))
            .andExpect(jsonPath("$.datenaissance").value(DEFAULT_DATENAISSANCE.toString()))
            .andExpect(jsonPath("$.daten").value(DEFAULT_DATEN.toString()));
    }

    @Test
    public void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDoctor() throws Exception {
        // Initialize the database
        doctorRepository.save(doctor);
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findOne(doctor.getId());
        updatedDoctor
                .motdepasse(UPDATED_MOTDEPASSE)
                .login(UPDATED_LOGIN)
                .servicemedi(UPDATED_SERVICEMEDI)
                .photo(UPDATED_PHOTO)
                .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
                .specialite(UPDATED_SPECIALITE)
                .nometprenom(UPDATED_NOMETPRENOM)
                .datenaissance(UPDATED_DATENAISSANCE)
                .daten(UPDATED_DATEN);

        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctor)))
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getMotdepasse()).isEqualTo(UPDATED_MOTDEPASSE);
        assertThat(testDoctor.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testDoctor.getServicemedi()).isEqualTo(UPDATED_SERVICEMEDI);
        assertThat(testDoctor.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDoctor.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testDoctor.getSpecialite()).isEqualTo(UPDATED_SPECIALITE);
        assertThat(testDoctor.getNometprenom()).isEqualTo(UPDATED_NOMETPRENOM);
        assertThat(testDoctor.getDatenaissance()).isEqualTo(UPDATED_DATENAISSANCE);
        assertThat(testDoctor.getDaten()).isEqualTo(UPDATED_DATEN);
    }

    @Test
    public void updateNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Create the Doctor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoctorMockMvc.perform(put("/api/doctors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doctor)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.save(doctor);
        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Get the doctor
        restDoctorMockMvc.perform(delete("/api/doctors/{id}", doctor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doctor.class);
    }
}
