package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Servicemedical;
import com.mycompany.myapp.repository.ServicemedicalRepository;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ServicemedicalResource REST controller.
 *
 * @see ServicemedicalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class ServicemedicalResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ServicemedicalRepository servicemedicalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restServicemedicalMockMvc;

    private Servicemedical servicemedical;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ServicemedicalResource servicemedicalResource = new ServicemedicalResource(servicemedicalRepository);
        this.restServicemedicalMockMvc = MockMvcBuilders.standaloneSetup(servicemedicalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicemedical createEntity() {
        Servicemedical servicemedical = new Servicemedical()
                .nom(DEFAULT_NOM)
                .image(DEFAULT_IMAGE)
                .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return servicemedical;
    }

    @Before
    public void initTest() {
        servicemedicalRepository.deleteAll();
        servicemedical = createEntity();
    }

    @Test
    public void createServicemedical() throws Exception {
        int databaseSizeBeforeCreate = servicemedicalRepository.findAll().size();

        // Create the Servicemedical

        restServicemedicalMockMvc.perform(post("/api/servicemedicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicemedical)))
            .andExpect(status().isCreated());

        // Validate the Servicemedical in the database
        List<Servicemedical> servicemedicalList = servicemedicalRepository.findAll();
        assertThat(servicemedicalList).hasSize(databaseSizeBeforeCreate + 1);
        Servicemedical testServicemedical = servicemedicalList.get(servicemedicalList.size() - 1);
        assertThat(testServicemedical.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testServicemedical.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testServicemedical.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    public void createServicemedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicemedicalRepository.findAll().size();

        // Create the Servicemedical with an existing ID
        Servicemedical existingServicemedical = new Servicemedical();
        existingServicemedical.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicemedicalMockMvc.perform(post("/api/servicemedicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingServicemedical)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Servicemedical> servicemedicalList = servicemedicalRepository.findAll();
        assertThat(servicemedicalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllServicemedicals() throws Exception {
        // Initialize the database
        servicemedicalRepository.save(servicemedical);

        // Get all the servicemedicalList
        restServicemedicalMockMvc.perform(get("/api/servicemedicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicemedical.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    public void getServicemedical() throws Exception {
        // Initialize the database
        servicemedicalRepository.save(servicemedical);

        // Get the servicemedical
        restServicemedicalMockMvc.perform(get("/api/servicemedicals/{id}", servicemedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicemedical.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    public void getNonExistingServicemedical() throws Exception {
        // Get the servicemedical
        restServicemedicalMockMvc.perform(get("/api/servicemedicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateServicemedical() throws Exception {
        // Initialize the database
        servicemedicalRepository.save(servicemedical);
        int databaseSizeBeforeUpdate = servicemedicalRepository.findAll().size();

        // Update the servicemedical
        Servicemedical updatedServicemedical = servicemedicalRepository.findOne(servicemedical.getId());
        updatedServicemedical
                .nom(UPDATED_NOM)
                .image(UPDATED_IMAGE)
                .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restServicemedicalMockMvc.perform(put("/api/servicemedicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServicemedical)))
            .andExpect(status().isOk());

        // Validate the Servicemedical in the database
        List<Servicemedical> servicemedicalList = servicemedicalRepository.findAll();
        assertThat(servicemedicalList).hasSize(databaseSizeBeforeUpdate);
        Servicemedical testServicemedical = servicemedicalList.get(servicemedicalList.size() - 1);
        assertThat(testServicemedical.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testServicemedical.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testServicemedical.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingServicemedical() throws Exception {
        int databaseSizeBeforeUpdate = servicemedicalRepository.findAll().size();

        // Create the Servicemedical

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServicemedicalMockMvc.perform(put("/api/servicemedicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicemedical)))
            .andExpect(status().isCreated());

        // Validate the Servicemedical in the database
        List<Servicemedical> servicemedicalList = servicemedicalRepository.findAll();
        assertThat(servicemedicalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteServicemedical() throws Exception {
        // Initialize the database
        servicemedicalRepository.save(servicemedical);
        int databaseSizeBeforeDelete = servicemedicalRepository.findAll().size();

        // Get the servicemedical
        restServicemedicalMockMvc.perform(delete("/api/servicemedicals/{id}", servicemedical.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Servicemedical> servicemedicalList = servicemedicalRepository.findAll();
        assertThat(servicemedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servicemedical.class);
    }
}
