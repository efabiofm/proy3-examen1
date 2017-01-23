package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Calificacion;
import com.cenfotec.escuelita.repository.CalificacionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CalificacionResource REST controller.
 *
 * @see CalificacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class CalificacionResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTA = 1;
    private static final Integer UPDATED_NOTA = 2;

    @Inject
    private CalificacionRepository calificacionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restCalificacionMockMvc;

    private Calificacion calificacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CalificacionResource calificacionResource = new CalificacionResource();
        ReflectionTestUtils.setField(calificacionResource, "calificacionRepository", calificacionRepository);
        this.restCalificacionMockMvc = MockMvcBuilders.standaloneSetup(calificacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calificacion createEntity(EntityManager em) {
        Calificacion calificacion = new Calificacion()
                .descripcion(DEFAULT_DESCRIPCION)
                .nota(DEFAULT_NOTA);
        return calificacion;
    }

    @Before
    public void initTest() {
        calificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalificacion() throws Exception {
        int databaseSizeBeforeCreate = calificacionRepository.findAll().size();

        // Create the Calificacion

        restCalificacionMockMvc.perform(post("/api/calificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calificacion)))
            .andExpect(status().isCreated());

        // Validate the Calificacion in the database
        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeCreate + 1);
        Calificacion testCalificacion = calificacionList.get(calificacionList.size() - 1);
        assertThat(testCalificacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCalificacion.getNota()).isEqualTo(DEFAULT_NOTA);
    }

    @Test
    @Transactional
    public void createCalificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calificacionRepository.findAll().size();

        // Create the Calificacion with an existing ID
        Calificacion existingCalificacion = new Calificacion();
        existingCalificacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalificacionMockMvc.perform(post("/api/calificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingCalificacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = calificacionRepository.findAll().size();
        // set the field null
        calificacion.setNota(null);

        // Create the Calificacion, which fails.

        restCalificacionMockMvc.perform(post("/api/calificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calificacion)))
            .andExpect(status().isBadRequest());

        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalificacions() throws Exception {
        // Initialize the database
        calificacionRepository.saveAndFlush(calificacion);

        // Get all the calificacionList
        restCalificacionMockMvc.perform(get("/api/calificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)));
    }

    @Test
    @Transactional
    public void getCalificacion() throws Exception {
        // Initialize the database
        calificacionRepository.saveAndFlush(calificacion);

        // Get the calificacion
        restCalificacionMockMvc.perform(get("/api/calificacions/{id}", calificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calificacion.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA));
    }

    @Test
    @Transactional
    public void getNonExistingCalificacion() throws Exception {
        // Get the calificacion
        restCalificacionMockMvc.perform(get("/api/calificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalificacion() throws Exception {
        // Initialize the database
        calificacionRepository.saveAndFlush(calificacion);
        int databaseSizeBeforeUpdate = calificacionRepository.findAll().size();

        // Update the calificacion
        Calificacion updatedCalificacion = calificacionRepository.findOne(calificacion.getId());
        updatedCalificacion
                .descripcion(UPDATED_DESCRIPCION)
                .nota(UPDATED_NOTA);

        restCalificacionMockMvc.perform(put("/api/calificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalificacion)))
            .andExpect(status().isOk());

        // Validate the Calificacion in the database
        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeUpdate);
        Calificacion testCalificacion = calificacionList.get(calificacionList.size() - 1);
        assertThat(testCalificacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCalificacion.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    public void updateNonExistingCalificacion() throws Exception {
        int databaseSizeBeforeUpdate = calificacionRepository.findAll().size();

        // Create the Calificacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCalificacionMockMvc.perform(put("/api/calificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calificacion)))
            .andExpect(status().isCreated());

        // Validate the Calificacion in the database
        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCalificacion() throws Exception {
        // Initialize the database
        calificacionRepository.saveAndFlush(calificacion);
        int databaseSizeBeforeDelete = calificacionRepository.findAll().size();

        // Get the calificacion
        restCalificacionMockMvc.perform(delete("/api/calificacions/{id}", calificacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calificacion> calificacionList = calificacionRepository.findAll();
        assertThat(calificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
