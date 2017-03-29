package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Fichepatient;
import com.mycompany.myapp.repository.FichepatientRepository;

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
 * Test class for the FichepatientResource REST controller.
 *
 * @see FichepatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class FichepatientResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_MEDID = "AAAAAAAAAA";
    private static final String UPDATED_MEDID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEDENAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDENAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANTECEDENTS = "AAAAAAAAAA";
    private static final String UPDATED_ANTECEDENTS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTATDUDERNIEREXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_RESULTATDUDERNIEREXAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_NOTESPROFITIONNELPARAMEDICAUX = "AAAAAAAAAA";
    private static final String UPDATED_NOTESPROFITIONNELPARAMEDICAUX = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPTERENDU = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPTERENDU = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMPTERENDU_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPTERENDU_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_RECOMMANDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMANDATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PERSCRIPTIONMEDICAMENTEUSES = "AAAAAAAAAA";
    private static final String UPDATED_PERSCRIPTIONMEDICAMENTEUSES = "BBBBBBBBBB";

    private static final String DEFAULT_GROUPESANGUIN = "AAAAAAAAAA";
    private static final String UPDATED_GROUPESANGUIN = "BBBBBBBBBB";

    @Autowired
    private FichepatientRepository fichepatientRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFichepatientMockMvc;

    private Fichepatient fichepatient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FichepatientResource fichepatientResource = new FichepatientResource(fichepatientRepository);
        this.restFichepatientMockMvc = MockMvcBuilders.standaloneSetup(fichepatientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichepatient createEntity() {
        Fichepatient fichepatient = new Fichepatient()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .medid(DEFAULT_MEDID)
                .datedenaissance(DEFAULT_DATEDENAISSANCE)
                .antecedents(DEFAULT_ANTECEDENTS)
                .resultatdudernierexamen(DEFAULT_RESULTATDUDERNIEREXAMEN)
                .notesprofitionnelparamedicaux(DEFAULT_NOTESPROFITIONNELPARAMEDICAUX)
                .compterendu(DEFAULT_COMPTERENDU)
                .compterenduContentType(DEFAULT_COMPTERENDU_CONTENT_TYPE)
                .recommandations(DEFAULT_RECOMMANDATIONS)
                .perscriptionmedicamenteuses(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES)
                .groupesanguin(DEFAULT_GROUPESANGUIN);
        return fichepatient;
    }

    @Before
    public void initTest() {
        fichepatientRepository.deleteAll();
        fichepatient = createEntity();
    }

    @Test
    public void createFichepatient() throws Exception {
        int databaseSizeBeforeCreate = fichepatientRepository.findAll().size();

        // Create the Fichepatient

        restFichepatientMockMvc.perform(post("/api/fichepatients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichepatient)))
            .andExpect(status().isCreated());

        // Validate the Fichepatient in the database
        List<Fichepatient> fichepatientList = fichepatientRepository.findAll();
        assertThat(fichepatientList).hasSize(databaseSizeBeforeCreate + 1);
        Fichepatient testFichepatient = fichepatientList.get(fichepatientList.size() - 1);
        assertThat(testFichepatient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFichepatient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFichepatient.getMedid()).isEqualTo(DEFAULT_MEDID);
        assertThat(testFichepatient.getDatedenaissance()).isEqualTo(DEFAULT_DATEDENAISSANCE);
        assertThat(testFichepatient.getAntecedents()).isEqualTo(DEFAULT_ANTECEDENTS);
        assertThat(testFichepatient.getResultatdudernierexamen()).isEqualTo(DEFAULT_RESULTATDUDERNIEREXAMEN);
        assertThat(testFichepatient.getNotesprofitionnelparamedicaux()).isEqualTo(DEFAULT_NOTESPROFITIONNELPARAMEDICAUX);
        assertThat(testFichepatient.getCompterendu()).isEqualTo(DEFAULT_COMPTERENDU);
        assertThat(testFichepatient.getCompterenduContentType()).isEqualTo(DEFAULT_COMPTERENDU_CONTENT_TYPE);
        assertThat(testFichepatient.getRecommandations()).isEqualTo(DEFAULT_RECOMMANDATIONS);
        assertThat(testFichepatient.getPerscriptionmedicamenteuses()).isEqualTo(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES);
        assertThat(testFichepatient.getGroupesanguin()).isEqualTo(DEFAULT_GROUPESANGUIN);
    }

    @Test
    public void createFichepatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichepatientRepository.findAll().size();

        // Create the Fichepatient with an existing ID
        Fichepatient existingFichepatient = new Fichepatient();
        existingFichepatient.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichepatientMockMvc.perform(post("/api/fichepatients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFichepatient)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fichepatient> fichepatientList = fichepatientRepository.findAll();
        assertThat(fichepatientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFichepatients() throws Exception {
        // Initialize the database
        fichepatientRepository.save(fichepatient);

        // Get all the fichepatientList
        restFichepatientMockMvc.perform(get("/api/fichepatients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichepatient.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].medid").value(hasItem(DEFAULT_MEDID.toString())))
            .andExpect(jsonPath("$.[*].datedenaissance").value(hasItem(DEFAULT_DATEDENAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].antecedents").value(hasItem(DEFAULT_ANTECEDENTS.toString())))
            .andExpect(jsonPath("$.[*].resultatdudernierexamen").value(hasItem(DEFAULT_RESULTATDUDERNIEREXAMEN.toString())))
            .andExpect(jsonPath("$.[*].notesprofitionnelparamedicaux").value(hasItem(DEFAULT_NOTESPROFITIONNELPARAMEDICAUX.toString())))
            .andExpect(jsonPath("$.[*].compterenduContentType").value(hasItem(DEFAULT_COMPTERENDU_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].compterendu").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPTERENDU))))
            .andExpect(jsonPath("$.[*].recommandations").value(hasItem(DEFAULT_RECOMMANDATIONS.toString())))
            .andExpect(jsonPath("$.[*].perscriptionmedicamenteuses").value(hasItem(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES.toString())))
            .andExpect(jsonPath("$.[*].groupesanguin").value(hasItem(DEFAULT_GROUPESANGUIN.toString())));
    }

    @Test
    public void getFichepatient() throws Exception {
        // Initialize the database
        fichepatientRepository.save(fichepatient);

        // Get the fichepatient
        restFichepatientMockMvc.perform(get("/api/fichepatients/{id}", fichepatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichepatient.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.medid").value(DEFAULT_MEDID.toString()))
            .andExpect(jsonPath("$.datedenaissance").value(DEFAULT_DATEDENAISSANCE.toString()))
            .andExpect(jsonPath("$.antecedents").value(DEFAULT_ANTECEDENTS.toString()))
            .andExpect(jsonPath("$.resultatdudernierexamen").value(DEFAULT_RESULTATDUDERNIEREXAMEN.toString()))
            .andExpect(jsonPath("$.notesprofitionnelparamedicaux").value(DEFAULT_NOTESPROFITIONNELPARAMEDICAUX.toString()))
            .andExpect(jsonPath("$.compterenduContentType").value(DEFAULT_COMPTERENDU_CONTENT_TYPE))
            .andExpect(jsonPath("$.compterendu").value(Base64Utils.encodeToString(DEFAULT_COMPTERENDU)))
            .andExpect(jsonPath("$.recommandations").value(DEFAULT_RECOMMANDATIONS.toString()))
            .andExpect(jsonPath("$.perscriptionmedicamenteuses").value(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES.toString()))
            .andExpect(jsonPath("$.groupesanguin").value(DEFAULT_GROUPESANGUIN.toString()));
    }

    @Test
    public void getNonExistingFichepatient() throws Exception {
        // Get the fichepatient
        restFichepatientMockMvc.perform(get("/api/fichepatients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFichepatient() throws Exception {
        // Initialize the database
        fichepatientRepository.save(fichepatient);
        int databaseSizeBeforeUpdate = fichepatientRepository.findAll().size();

        // Update the fichepatient
        Fichepatient updatedFichepatient = fichepatientRepository.findOne(fichepatient.getId());
        updatedFichepatient
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .medid(UPDATED_MEDID)
                .datedenaissance(UPDATED_DATEDENAISSANCE)
                .antecedents(UPDATED_ANTECEDENTS)
                .resultatdudernierexamen(UPDATED_RESULTATDUDERNIEREXAMEN)
                .notesprofitionnelparamedicaux(UPDATED_NOTESPROFITIONNELPARAMEDICAUX)
                .compterendu(UPDATED_COMPTERENDU)
                .compterenduContentType(UPDATED_COMPTERENDU_CONTENT_TYPE)
                .recommandations(UPDATED_RECOMMANDATIONS)
                .perscriptionmedicamenteuses(UPDATED_PERSCRIPTIONMEDICAMENTEUSES)
                .groupesanguin(UPDATED_GROUPESANGUIN);

        restFichepatientMockMvc.perform(put("/api/fichepatients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichepatient)))
            .andExpect(status().isOk());

        // Validate the Fichepatient in the database
        List<Fichepatient> fichepatientList = fichepatientRepository.findAll();
        assertThat(fichepatientList).hasSize(databaseSizeBeforeUpdate);
        Fichepatient testFichepatient = fichepatientList.get(fichepatientList.size() - 1);
        assertThat(testFichepatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFichepatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFichepatient.getMedid()).isEqualTo(UPDATED_MEDID);
        assertThat(testFichepatient.getDatedenaissance()).isEqualTo(UPDATED_DATEDENAISSANCE);
        assertThat(testFichepatient.getAntecedents()).isEqualTo(UPDATED_ANTECEDENTS);
        assertThat(testFichepatient.getResultatdudernierexamen()).isEqualTo(UPDATED_RESULTATDUDERNIEREXAMEN);
        assertThat(testFichepatient.getNotesprofitionnelparamedicaux()).isEqualTo(UPDATED_NOTESPROFITIONNELPARAMEDICAUX);
        assertThat(testFichepatient.getCompterendu()).isEqualTo(UPDATED_COMPTERENDU);
        assertThat(testFichepatient.getCompterenduContentType()).isEqualTo(UPDATED_COMPTERENDU_CONTENT_TYPE);
        assertThat(testFichepatient.getRecommandations()).isEqualTo(UPDATED_RECOMMANDATIONS);
        assertThat(testFichepatient.getPerscriptionmedicamenteuses()).isEqualTo(UPDATED_PERSCRIPTIONMEDICAMENTEUSES);
        assertThat(testFichepatient.getGroupesanguin()).isEqualTo(UPDATED_GROUPESANGUIN);
    }

    @Test
    public void updateNonExistingFichepatient() throws Exception {
        int databaseSizeBeforeUpdate = fichepatientRepository.findAll().size();

        // Create the Fichepatient

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFichepatientMockMvc.perform(put("/api/fichepatients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichepatient)))
            .andExpect(status().isCreated());

        // Validate the Fichepatient in the database
        List<Fichepatient> fichepatientList = fichepatientRepository.findAll();
        assertThat(fichepatientList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFichepatient() throws Exception {
        // Initialize the database
        fichepatientRepository.save(fichepatient);
        int databaseSizeBeforeDelete = fichepatientRepository.findAll().size();

        // Get the fichepatient
        restFichepatientMockMvc.perform(delete("/api/fichepatients/{id}", fichepatient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fichepatient> fichepatientList = fichepatientRepository.findAll();
        assertThat(fichepatientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fichepatient.class);
    }
}
