package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Entrenamiento;
import com.cenfotec.escuelita.repository.EntrenamientoRepository;

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
 * Test class for the EntrenamientoResource REST controller.
 *
 * @see EntrenamientoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class EntrenamientoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrenamientoMockMvc;

    private Entrenamiento entrenamiento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrenamientoResource entrenamientoResource = new EntrenamientoResource();
        ReflectionTestUtils.setField(entrenamientoResource, "entrenamientoRepository", entrenamientoRepository);
        this.restEntrenamientoMockMvc = MockMvcBuilders.standaloneSetup(entrenamientoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrenamiento createEntity(EntityManager em) {
        Entrenamiento entrenamiento = new Entrenamiento()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION);
        return entrenamiento;
    }

    @Before
    public void initTest() {
        entrenamiento = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrenamiento() throws Exception {
        int databaseSizeBeforeCreate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamiento)))
            .andExpect(status().isCreated());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createEntrenamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento with an existing ID
        Entrenamiento existingEntrenamiento = new Entrenamiento();
        existingEntrenamiento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEntrenamiento)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenamientoRepository.findAll().size();
        // set the field null
        entrenamiento.setNombre(null);

        // Create the Entrenamiento, which fails.

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamiento)))
            .andExpect(status().isBadRequest());

        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntrenamientos() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get all the entrenamientoList
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrenamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos/{id}", entrenamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrenamiento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntrenamiento() throws Exception {
        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(get("/api/entrenamientos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);
        int databaseSizeBeforeUpdate = entrenamientoRepository.findAll().size();

        // Update the entrenamiento
        Entrenamiento updatedEntrenamiento = entrenamientoRepository.findOne(entrenamiento.getId());
        updatedEntrenamiento
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION);

        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntrenamiento)))
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeUpdate);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrenamiento() throws Exception {
        int databaseSizeBeforeUpdate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamiento)))
            .andExpect(status().isCreated());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntrenamiento() throws Exception {
        // Initialize the database
        entrenamientoRepository.saveAndFlush(entrenamiento);
        int databaseSizeBeforeDelete = entrenamientoRepository.findAll().size();

        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(delete("/api/entrenamientos/{id}", entrenamiento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
