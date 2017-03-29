package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Medecin;
import com.mycompany.myapp.repository.MedecinRepository;

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
 * Test class for the MedecinResource REST controller.
 *
 * @see MedecinResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class MedecinResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATENAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SERVICEID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICEID = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTDEPASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOTDEPASSE = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMR = "AAAAAAAAAA";
    private static final String UPDATED_NUMR = "BBBBBBBBBB";

    private static final String DEFAULT_NUMER = "AAAAAAAAAA";
    private static final String UPDATED_NUMER = "BBBBBBBBBB";

    private static final String DEFAULT_AZ = "AAAAAAAAAA";
    private static final String UPDATED_AZ = "BBBBBBBBBB";

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMedecinMockMvc;

    private Medecin medecin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MedecinResource medecinResource = new MedecinResource(medecinRepository);
        this.restMedecinMockMvc = MockMvcBuilders.standaloneSetup(medecinResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createEntity() {
        Medecin medecin = new Medecin()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .datenaissance(DEFAULT_DATENAISSANCE)
                .serviceid(DEFAULT_SERVICEID)
                .specialite(DEFAULT_SPECIALITE)
                .photo(DEFAULT_PHOTO)
                .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
                .login(DEFAULT_LOGIN)
                .fullName(DEFAULT_FULL_NAME)
                .motdepasse(DEFAULT_MOTDEPASSE)
                .numero(DEFAULT_NUMERO)
                .numr(DEFAULT_NUMR)
                .numer(DEFAULT_NUMER)
                .az(DEFAULT_AZ);
        return medecin;
    }

    @Before
    public void initTest() {
        medecinRepository.deleteAll();
        medecin = createEntity();
    }

    @Test
    public void createMedecin() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // Create the Medecin

        restMedecinMockMvc.perform(post("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isCreated());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate + 1);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMedecin.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testMedecin.getDatenaissance()).isEqualTo(DEFAULT_DATENAISSANCE);
        assertThat(testMedecin.getServiceid()).isEqualTo(DEFAULT_SERVICEID);
        assertThat(testMedecin.getSpecialite()).isEqualTo(DEFAULT_SPECIALITE);
        assertThat(testMedecin.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testMedecin.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testMedecin.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testMedecin.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testMedecin.getMotdepasse()).isEqualTo(DEFAULT_MOTDEPASSE);
        assertThat(testMedecin.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testMedecin.getNumr()).isEqualTo(DEFAULT_NUMR);
        assertThat(testMedecin.getNumer()).isEqualTo(DEFAULT_NUMER);
        assertThat(testMedecin.getAz()).isEqualTo(DEFAULT_AZ);
    }

    @Test
    public void createMedecinWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // Create the Medecin with an existing ID
        Medecin existingMedecin = new Medecin();
        existingMedecin.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedecinMockMvc.perform(post("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMedecin)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMedecins() throws Exception {
        // Initialize the database
        medecinRepository.save(medecin);

        // Get all the medecinList
        restMedecinMockMvc.perform(get("/api/medecins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medecin.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].datenaissance").value(hasItem(DEFAULT_DATENAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].serviceid").value(hasItem(DEFAULT_SERVICEID.toString())))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].motdepasse").value(hasItem(DEFAULT_MOTDEPASSE.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].numr").value(hasItem(DEFAULT_NUMR.toString())))
            .andExpect(jsonPath("$.[*].numer").value(hasItem(DEFAULT_NUMER.toString())))
            .andExpect(jsonPath("$.[*].az").value(hasItem(DEFAULT_AZ.toString())));
    }

    @Test
    public void getMedecin() throws Exception {
        // Initialize the database
        medecinRepository.save(medecin);

        // Get the medecin
        restMedecinMockMvc.perform(get("/api/medecins/{id}", medecin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medecin.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.datenaissance").value(DEFAULT_DATENAISSANCE.toString()))
            .andExpect(jsonPath("$.serviceid").value(DEFAULT_SERVICEID.toString()))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.motdepasse").value(DEFAULT_MOTDEPASSE.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.numr").value(DEFAULT_NUMR.toString()))
            .andExpect(jsonPath("$.numer").value(DEFAULT_NUMER.toString()))
            .andExpect(jsonPath("$.az").value(DEFAULT_AZ.toString()));
    }

    @Test
    public void getNonExistingMedecin() throws Exception {
        // Get the medecin
        restMedecinMockMvc.perform(get("/api/medecins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMedecin() throws Exception {
        // Initialize the database
        medecinRepository.save(medecin);
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin
        Medecin updatedMedecin = medecinRepository.findOne(medecin.getId());
        updatedMedecin
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .datenaissance(UPDATED_DATENAISSANCE)
                .serviceid(UPDATED_SERVICEID)
                .specialite(UPDATED_SPECIALITE)
                .photo(UPDATED_PHOTO)
                .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
                .login(UPDATED_LOGIN)
                .fullName(UPDATED_FULL_NAME)
                .motdepasse(UPDATED_MOTDEPASSE)
                .numero(UPDATED_NUMERO)
                .numr(UPDATED_NUMR)
                .numer(UPDATED_NUMER)
                .az(UPDATED_AZ);

        restMedecinMockMvc.perform(put("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedecin)))
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMedecin.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testMedecin.getDatenaissance()).isEqualTo(UPDATED_DATENAISSANCE);
        assertThat(testMedecin.getServiceid()).isEqualTo(UPDATED_SERVICEID);
        assertThat(testMedecin.getSpecialite()).isEqualTo(UPDATED_SPECIALITE);
        assertThat(testMedecin.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testMedecin.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testMedecin.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testMedecin.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testMedecin.getMotdepasse()).isEqualTo(UPDATED_MOTDEPASSE);
        assertThat(testMedecin.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testMedecin.getNumr()).isEqualTo(UPDATED_NUMR);
        assertThat(testMedecin.getNumer()).isEqualTo(UPDATED_NUMER);
        assertThat(testMedecin.getAz()).isEqualTo(UPDATED_AZ);
    }

    @Test
    public void updateNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Create the Medecin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedecinMockMvc.perform(put("/api/medecins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medecin)))
            .andExpect(status().isCreated());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMedecin() throws Exception {
        // Initialize the database
        medecinRepository.save(medecin);
        int databaseSizeBeforeDelete = medecinRepository.findAll().size();

        // Get the medecin
        restMedecinMockMvc.perform(delete("/api/medecins/{id}", medecin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medecin.class);
    }
}
