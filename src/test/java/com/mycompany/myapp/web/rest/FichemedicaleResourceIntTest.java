package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Fichemedicale;
import com.mycompany.myapp.repository.FichemedicaleRepository;

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
 * Test class for the FichemedicaleResource REST controller.
 *
 * @see FichemedicaleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class FichemedicaleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATENAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATENAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ANTECEDENTS = "AAAAAAAAAA";
    private static final String UPDATED_ANTECEDENTS = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTATDUDERNIEREXAMEN = "AAAAAAAAAA";
    private static final String UPDATED_RESULTATDUDERNIEREXAMEN = "BBBBBBBBBB";

    private static final String DEFAULT_NOTESPROFITIONNELSPARAMEDICAUX = "AAAAAAAAAA";
    private static final String UPDATED_NOTESPROFITIONNELSPARAMEDICAUX = "BBBBBBBBBB";

    private static final String DEFAULT_COMPTESRENDUS = "AAAAAAAAAA";
    private static final String UPDATED_COMPTESRENDUS = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMMANDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMANDATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_PERSCRIPTIONMEDICAMENTEUSES = "AAAAAAAAAA";
    private static final String UPDATED_PERSCRIPTIONMEDICAMENTEUSES = "BBBBBBBBBB";

    private static final String DEFAULT_GROUPESANGUIN = "AAAAAAAAAA";
    private static final String UPDATED_GROUPESANGUIN = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMPTE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMPTE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COMPTE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMPTE_CONTENT_TYPE = "image/png";

    @Autowired
    private FichemedicaleRepository fichemedicaleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFichemedicaleMockMvc;

    private Fichemedicale fichemedicale;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            FichemedicaleResource fichemedicaleResource = new FichemedicaleResource(fichemedicaleRepository);
        this.restFichemedicaleMockMvc = MockMvcBuilders.standaloneSetup(fichemedicaleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichemedicale createEntity() {
        Fichemedicale fichemedicale = new Fichemedicale()
                .nom(DEFAULT_NOM)
                .prenom(DEFAULT_PRENOM)
                .datenaissance(DEFAULT_DATENAISSANCE)
                .antecedents(DEFAULT_ANTECEDENTS)
                .resultatdudernierexamen(DEFAULT_RESULTATDUDERNIEREXAMEN)
                .notesprofitionnelsparamedicaux(DEFAULT_NOTESPROFITIONNELSPARAMEDICAUX)
                .comptesrendus(DEFAULT_COMPTESRENDUS)
                .recommandations(DEFAULT_RECOMMANDATIONS)
                .perscriptionmedicamenteuses(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES)
                .groupesanguin(DEFAULT_GROUPESANGUIN)
                .compte(DEFAULT_COMPTE)
                .compteContentType(DEFAULT_COMPTE_CONTENT_TYPE);
        return fichemedicale;
    }

    @Before
    public void initTest() {
        fichemedicaleRepository.deleteAll();
        fichemedicale = createEntity();
    }

    @Test
    public void createFichemedicale() throws Exception {
        int databaseSizeBeforeCreate = fichemedicaleRepository.findAll().size();

        // Create the Fichemedicale

        restFichemedicaleMockMvc.perform(post("/api/fichemedicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichemedicale)))
            .andExpect(status().isCreated());

        // Validate the Fichemedicale in the database
        List<Fichemedicale> fichemedicaleList = fichemedicaleRepository.findAll();
        assertThat(fichemedicaleList).hasSize(databaseSizeBeforeCreate + 1);
        Fichemedicale testFichemedicale = fichemedicaleList.get(fichemedicaleList.size() - 1);
        assertThat(testFichemedicale.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testFichemedicale.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testFichemedicale.getDatenaissance()).isEqualTo(DEFAULT_DATENAISSANCE);
        assertThat(testFichemedicale.getAntecedents()).isEqualTo(DEFAULT_ANTECEDENTS);
        assertThat(testFichemedicale.getResultatdudernierexamen()).isEqualTo(DEFAULT_RESULTATDUDERNIEREXAMEN);
        assertThat(testFichemedicale.getNotesprofitionnelsparamedicaux()).isEqualTo(DEFAULT_NOTESPROFITIONNELSPARAMEDICAUX);
        assertThat(testFichemedicale.getComptesrendus()).isEqualTo(DEFAULT_COMPTESRENDUS);
        assertThat(testFichemedicale.getRecommandations()).isEqualTo(DEFAULT_RECOMMANDATIONS);
        assertThat(testFichemedicale.getPerscriptionmedicamenteuses()).isEqualTo(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES);
        assertThat(testFichemedicale.getGroupesanguin()).isEqualTo(DEFAULT_GROUPESANGUIN);
        assertThat(testFichemedicale.getCompte()).isEqualTo(DEFAULT_COMPTE);
        assertThat(testFichemedicale.getCompteContentType()).isEqualTo(DEFAULT_COMPTE_CONTENT_TYPE);
    }

    @Test
    public void createFichemedicaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichemedicaleRepository.findAll().size();

        // Create the Fichemedicale with an existing ID
        Fichemedicale existingFichemedicale = new Fichemedicale();
        existingFichemedicale.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichemedicaleMockMvc.perform(post("/api/fichemedicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingFichemedicale)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fichemedicale> fichemedicaleList = fichemedicaleRepository.findAll();
        assertThat(fichemedicaleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllFichemedicales() throws Exception {
        // Initialize the database
        fichemedicaleRepository.save(fichemedicale);

        // Get all the fichemedicaleList
        restFichemedicaleMockMvc.perform(get("/api/fichemedicales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichemedicale.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].datenaissance").value(hasItem(DEFAULT_DATENAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].antecedents").value(hasItem(DEFAULT_ANTECEDENTS.toString())))
            .andExpect(jsonPath("$.[*].resultatdudernierexamen").value(hasItem(DEFAULT_RESULTATDUDERNIEREXAMEN.toString())))
            .andExpect(jsonPath("$.[*].notesprofitionnelsparamedicaux").value(hasItem(DEFAULT_NOTESPROFITIONNELSPARAMEDICAUX.toString())))
            .andExpect(jsonPath("$.[*].comptesrendus").value(hasItem(DEFAULT_COMPTESRENDUS.toString())))
            .andExpect(jsonPath("$.[*].recommandations").value(hasItem(DEFAULT_RECOMMANDATIONS.toString())))
            .andExpect(jsonPath("$.[*].perscriptionmedicamenteuses").value(hasItem(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES.toString())))
            .andExpect(jsonPath("$.[*].groupesanguin").value(hasItem(DEFAULT_GROUPESANGUIN.toString())))
            .andExpect(jsonPath("$.[*].compteContentType").value(hasItem(DEFAULT_COMPTE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].compte").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMPTE))));
    }

    @Test
    public void getFichemedicale() throws Exception {
        // Initialize the database
        fichemedicaleRepository.save(fichemedicale);

        // Get the fichemedicale
        restFichemedicaleMockMvc.perform(get("/api/fichemedicales/{id}", fichemedicale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichemedicale.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.datenaissance").value(DEFAULT_DATENAISSANCE.toString()))
            .andExpect(jsonPath("$.antecedents").value(DEFAULT_ANTECEDENTS.toString()))
            .andExpect(jsonPath("$.resultatdudernierexamen").value(DEFAULT_RESULTATDUDERNIEREXAMEN.toString()))
            .andExpect(jsonPath("$.notesprofitionnelsparamedicaux").value(DEFAULT_NOTESPROFITIONNELSPARAMEDICAUX.toString()))
            .andExpect(jsonPath("$.comptesrendus").value(DEFAULT_COMPTESRENDUS.toString()))
            .andExpect(jsonPath("$.recommandations").value(DEFAULT_RECOMMANDATIONS.toString()))
            .andExpect(jsonPath("$.perscriptionmedicamenteuses").value(DEFAULT_PERSCRIPTIONMEDICAMENTEUSES.toString()))
            .andExpect(jsonPath("$.groupesanguin").value(DEFAULT_GROUPESANGUIN.toString()))
            .andExpect(jsonPath("$.compteContentType").value(DEFAULT_COMPTE_CONTENT_TYPE))
            .andExpect(jsonPath("$.compte").value(Base64Utils.encodeToString(DEFAULT_COMPTE)));
    }

    @Test
    public void getNonExistingFichemedicale() throws Exception {
        // Get the fichemedicale
        restFichemedicaleMockMvc.perform(get("/api/fichemedicales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFichemedicale() throws Exception {
        // Initialize the database
        fichemedicaleRepository.save(fichemedicale);
        int databaseSizeBeforeUpdate = fichemedicaleRepository.findAll().size();

        // Update the fichemedicale
        Fichemedicale updatedFichemedicale = fichemedicaleRepository.findOne(fichemedicale.getId());
        updatedFichemedicale
                .nom(UPDATED_NOM)
                .prenom(UPDATED_PRENOM)
                .datenaissance(UPDATED_DATENAISSANCE)
                .antecedents(UPDATED_ANTECEDENTS)
                .resultatdudernierexamen(UPDATED_RESULTATDUDERNIEREXAMEN)
                .notesprofitionnelsparamedicaux(UPDATED_NOTESPROFITIONNELSPARAMEDICAUX)
                .comptesrendus(UPDATED_COMPTESRENDUS)
                .recommandations(UPDATED_RECOMMANDATIONS)
                .perscriptionmedicamenteuses(UPDATED_PERSCRIPTIONMEDICAMENTEUSES)
                .groupesanguin(UPDATED_GROUPESANGUIN)
                .compte(UPDATED_COMPTE)
                .compteContentType(UPDATED_COMPTE_CONTENT_TYPE);

        restFichemedicaleMockMvc.perform(put("/api/fichemedicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichemedicale)))
            .andExpect(status().isOk());

        // Validate the Fichemedicale in the database
        List<Fichemedicale> fichemedicaleList = fichemedicaleRepository.findAll();
        assertThat(fichemedicaleList).hasSize(databaseSizeBeforeUpdate);
        Fichemedicale testFichemedicale = fichemedicaleList.get(fichemedicaleList.size() - 1);
        assertThat(testFichemedicale.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testFichemedicale.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testFichemedicale.getDatenaissance()).isEqualTo(UPDATED_DATENAISSANCE);
        assertThat(testFichemedicale.getAntecedents()).isEqualTo(UPDATED_ANTECEDENTS);
        assertThat(testFichemedicale.getResultatdudernierexamen()).isEqualTo(UPDATED_RESULTATDUDERNIEREXAMEN);
        assertThat(testFichemedicale.getNotesprofitionnelsparamedicaux()).isEqualTo(UPDATED_NOTESPROFITIONNELSPARAMEDICAUX);
        assertThat(testFichemedicale.getComptesrendus()).isEqualTo(UPDATED_COMPTESRENDUS);
        assertThat(testFichemedicale.getRecommandations()).isEqualTo(UPDATED_RECOMMANDATIONS);
        assertThat(testFichemedicale.getPerscriptionmedicamenteuses()).isEqualTo(UPDATED_PERSCRIPTIONMEDICAMENTEUSES);
        assertThat(testFichemedicale.getGroupesanguin()).isEqualTo(UPDATED_GROUPESANGUIN);
        assertThat(testFichemedicale.getCompte()).isEqualTo(UPDATED_COMPTE);
        assertThat(testFichemedicale.getCompteContentType()).isEqualTo(UPDATED_COMPTE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingFichemedicale() throws Exception {
        int databaseSizeBeforeUpdate = fichemedicaleRepository.findAll().size();

        // Create the Fichemedicale

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFichemedicaleMockMvc.perform(put("/api/fichemedicales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichemedicale)))
            .andExpect(status().isCreated());

        // Validate the Fichemedicale in the database
        List<Fichemedicale> fichemedicaleList = fichemedicaleRepository.findAll();
        assertThat(fichemedicaleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFichemedicale() throws Exception {
        // Initialize the database
        fichemedicaleRepository.save(fichemedicale);
        int databaseSizeBeforeDelete = fichemedicaleRepository.findAll().size();

        // Get the fichemedicale
        restFichemedicaleMockMvc.perform(delete("/api/fichemedicales/{id}", fichemedicale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fichemedicale> fichemedicaleList = fichemedicaleRepository.findAll();
        assertThat(fichemedicaleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fichemedicale.class);
    }
}
