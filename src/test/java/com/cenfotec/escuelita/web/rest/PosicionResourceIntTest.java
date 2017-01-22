package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Posicion;
import com.cenfotec.escuelita.repository.PosicionRepository;

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
 * Test class for the PosicionResource REST controller.
 *
 * @see PosicionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class PosicionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Inject
    private PosicionRepository posicionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPosicionMockMvc;

    private Posicion posicion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PosicionResource posicionResource = new PosicionResource();
        ReflectionTestUtils.setField(posicionResource, "posicionRepository", posicionRepository);
        this.restPosicionMockMvc = MockMvcBuilders.standaloneSetup(posicionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posicion createEntity(EntityManager em) {
        Posicion posicion = new Posicion()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION);
        return posicion;
    }

    @Before
    public void initTest() {
        posicion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPosicion() throws Exception {
        int databaseSizeBeforeCreate = posicionRepository.findAll().size();

        // Create the Posicion

        restPosicionMockMvc.perform(post("/api/posicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posicion)))
            .andExpect(status().isCreated());

        // Validate the Posicion in the database
        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeCreate + 1);
        Posicion testPosicion = posicionList.get(posicionList.size() - 1);
        assertThat(testPosicion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPosicion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createPosicionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = posicionRepository.findAll().size();

        // Create the Posicion with an existing ID
        Posicion existingPosicion = new Posicion();
        existingPosicion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPosicionMockMvc.perform(post("/api/posicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPosicion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = posicionRepository.findAll().size();
        // set the field null
        posicion.setNombre(null);

        // Create the Posicion, which fails.

        restPosicionMockMvc.perform(post("/api/posicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posicion)))
            .andExpect(status().isBadRequest());

        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPosicions() throws Exception {
        // Initialize the database
        posicionRepository.saveAndFlush(posicion);

        // Get all the posicionList
        restPosicionMockMvc.perform(get("/api/posicions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posicion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getPosicion() throws Exception {
        // Initialize the database
        posicionRepository.saveAndFlush(posicion);

        // Get the posicion
        restPosicionMockMvc.perform(get("/api/posicions/{id}", posicion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(posicion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPosicion() throws Exception {
        // Get the posicion
        restPosicionMockMvc.perform(get("/api/posicions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePosicion() throws Exception {
        // Initialize the database
        posicionRepository.saveAndFlush(posicion);
        int databaseSizeBeforeUpdate = posicionRepository.findAll().size();

        // Update the posicion
        Posicion updatedPosicion = posicionRepository.findOne(posicion.getId());
        updatedPosicion
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION);

        restPosicionMockMvc.perform(put("/api/posicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPosicion)))
            .andExpect(status().isOk());

        // Validate the Posicion in the database
        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeUpdate);
        Posicion testPosicion = posicionList.get(posicionList.size() - 1);
        assertThat(testPosicion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPosicion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingPosicion() throws Exception {
        int databaseSizeBeforeUpdate = posicionRepository.findAll().size();

        // Create the Posicion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPosicionMockMvc.perform(put("/api/posicions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(posicion)))
            .andExpect(status().isCreated());

        // Validate the Posicion in the database
        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePosicion() throws Exception {
        // Initialize the database
        posicionRepository.saveAndFlush(posicion);
        int databaseSizeBeforeDelete = posicionRepository.findAll().size();

        // Get the posicion
        restPosicionMockMvc.perform(delete("/api/posicions/{id}", posicion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Posicion> posicionList = posicionRepository.findAll();
        assertThat(posicionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
