package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AvancementApp;

import com.mycompany.myapp.domain.Hello;
import com.mycompany.myapp.repository.HelloRepository;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HelloResource REST controller.
 *
 * @see HelloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AvancementApp.class)
public class HelloResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAMENTID = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAMENTID = "BBBBBBBBBB";

    private static final String DEFAULT_MALADIE = "AAAAAAAAAA";
    private static final String UPDATED_MALADIE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMPTOMES = "AAAAAAAAAA";
    private static final String UPDATED_SYMPTOMES = "BBBBBBBBBB";

    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHelloMockMvc;

    private Hello hello;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            HelloResource helloResource = new HelloResource(helloRepository);
        this.restHelloMockMvc = MockMvcBuilders.standaloneSetup(helloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hello createEntity() {
        Hello hello = new Hello()
                .name(DEFAULT_NAME)
                .medicamentid(DEFAULT_MEDICAMENTID)
                .maladie(DEFAULT_MALADIE)
                .symptomes(DEFAULT_SYMPTOMES);
        return hello;
    }

    @Before
    public void initTest() {
        helloRepository.deleteAll();
        hello = createEntity();
    }

    @Test
    public void createHello() throws Exception {
        int databaseSizeBeforeCreate = helloRepository.findAll().size();

        // Create the Hello

        restHelloMockMvc.perform(post("/api/hellos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hello)))
            .andExpect(status().isCreated());

        // Validate the Hello in the database
        List<Hello> helloList = helloRepository.findAll();
        assertThat(helloList).hasSize(databaseSizeBeforeCreate + 1);
        Hello testHello = helloList.get(helloList.size() - 1);
        assertThat(testHello.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHello.getMedicamentid()).isEqualTo(DEFAULT_MEDICAMENTID);
        assertThat(testHello.getMaladie()).isEqualTo(DEFAULT_MALADIE);
        assertThat(testHello.getSymptomes()).isEqualTo(DEFAULT_SYMPTOMES);
    }

    @Test
    public void createHelloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = helloRepository.findAll().size();

        // Create the Hello with an existing ID
        Hello existingHello = new Hello();
        existingHello.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restHelloMockMvc.perform(post("/api/hellos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHello)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Hello> helloList = helloRepository.findAll();
        assertThat(helloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllHellos() throws Exception {
        // Initialize the database
        helloRepository.save(hello);

        // Get all the helloList
        restHelloMockMvc.perform(get("/api/hellos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hello.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].medicamentid").value(hasItem(DEFAULT_MEDICAMENTID.toString())))
            .andExpect(jsonPath("$.[*].maladie").value(hasItem(DEFAULT_MALADIE.toString())))
            .andExpect(jsonPath("$.[*].symptomes").value(hasItem(DEFAULT_SYMPTOMES.toString())));
    }

    @Test
    public void getHello() throws Exception {
        // Initialize the database
        helloRepository.save(hello);

        // Get the hello
        restHelloMockMvc.perform(get("/api/hellos/{id}", hello.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hello.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.medicamentid").value(DEFAULT_MEDICAMENTID.toString()))
            .andExpect(jsonPath("$.maladie").value(DEFAULT_MALADIE.toString()))
            .andExpect(jsonPath("$.symptomes").value(DEFAULT_SYMPTOMES.toString()));
    }

    @Test
    public void getNonExistingHello() throws Exception {
        // Get the hello
        restHelloMockMvc.perform(get("/api/hellos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateHello() throws Exception {
        // Initialize the database
        helloRepository.save(hello);
        int databaseSizeBeforeUpdate = helloRepository.findAll().size();

        // Update the hello
        Hello updatedHello = helloRepository.findOne(hello.getId());
        updatedHello
                .name(UPDATED_NAME)
                .medicamentid(UPDATED_MEDICAMENTID)
                .maladie(UPDATED_MALADIE)
                .symptomes(UPDATED_SYMPTOMES);

        restHelloMockMvc.perform(put("/api/hellos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHello)))
            .andExpect(status().isOk());

        // Validate the Hello in the database
        List<Hello> helloList = helloRepository.findAll();
        assertThat(helloList).hasSize(databaseSizeBeforeUpdate);
        Hello testHello = helloList.get(helloList.size() - 1);
        assertThat(testHello.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHello.getMedicamentid()).isEqualTo(UPDATED_MEDICAMENTID);
        assertThat(testHello.getMaladie()).isEqualTo(UPDATED_MALADIE);
        assertThat(testHello.getSymptomes()).isEqualTo(UPDATED_SYMPTOMES);
    }

    @Test
    public void updateNonExistingHello() throws Exception {
        int databaseSizeBeforeUpdate = helloRepository.findAll().size();

        // Create the Hello

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHelloMockMvc.perform(put("/api/hellos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hello)))
            .andExpect(status().isCreated());

        // Validate the Hello in the database
        List<Hello> helloList = helloRepository.findAll();
        assertThat(helloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteHello() throws Exception {
        // Initialize the database
        helloRepository.save(hello);
        int databaseSizeBeforeDelete = helloRepository.findAll().size();

        // Get the hello
        restHelloMockMvc.perform(delete("/api/hellos/{id}", hello.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Hello> helloList = helloRepository.findAll();
        assertThat(helloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hello.class);
    }
}
