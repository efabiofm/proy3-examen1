package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Entrenador;
import com.cenfotec.escuelita.repository.EntrenadorRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntrenadorResource REST controller.
 *
 * @see EntrenadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class EntrenadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    @Inject
    private EntrenadorRepository entrenadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEntrenadorMockMvc;

    private Entrenador entrenador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EntrenadorResource entrenadorResource = new EntrenadorResource();
        ReflectionTestUtils.setField(entrenadorResource, "entrenadorRepository", entrenadorRepository);
        this.restEntrenadorMockMvc = MockMvcBuilders.standaloneSetup(entrenadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrenador createEntity(EntityManager em) {
        Entrenador entrenador = new Entrenador()
                .nombre(DEFAULT_NOMBRE)
                .apellido(DEFAULT_APELLIDO)
                .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
                .telefono(DEFAULT_TELEFONO)
                .correo(DEFAULT_CORREO);
        return entrenador;
    }

    @Before
    public void initTest() {
        entrenador = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrenador() throws Exception {
        int databaseSizeBeforeCreate = entrenadorRepository.findAll().size();

        // Create the Entrenador

        restEntrenadorMockMvc.perform(post("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenador)))
            .andExpect(status().isCreated());

        // Validate the Entrenador in the database
        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeCreate + 1);
        Entrenador testEntrenador = entrenadorList.get(entrenadorList.size() - 1);
        assertThat(testEntrenador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEntrenador.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testEntrenador.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testEntrenador.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEntrenador.getCorreo()).isEqualTo(DEFAULT_CORREO);
    }

    @Test
    @Transactional
    public void createEntrenadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrenadorRepository.findAll().size();

        // Create the Entrenador with an existing ID
        Entrenador existingEntrenador = new Entrenador();
        existingEntrenador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrenadorMockMvc.perform(post("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEntrenador)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenadorRepository.findAll().size();
        // set the field null
        entrenador.setNombre(null);

        // Create the Entrenador, which fails.

        restEntrenadorMockMvc.perform(post("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenador)))
            .andExpect(status().isBadRequest());

        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = entrenadorRepository.findAll().size();
        // set the field null
        entrenador.setFechaNacimiento(null);

        // Create the Entrenador, which fails.

        restEntrenadorMockMvc.perform(post("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenador)))
            .andExpect(status().isBadRequest());

        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEntrenadors() throws Exception {
        // Initialize the database
        entrenadorRepository.saveAndFlush(entrenador);

        // Get all the entrenadorList
        restEntrenadorMockMvc.perform(get("/api/entrenadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrenador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())));
    }

    @Test
    @Transactional
    public void getEntrenador() throws Exception {
        // Initialize the database
        entrenadorRepository.saveAndFlush(entrenador);

        // Get the entrenador
        restEntrenadorMockMvc.perform(get("/api/entrenadors/{id}", entrenador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrenador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntrenador() throws Exception {
        // Get the entrenador
        restEntrenadorMockMvc.perform(get("/api/entrenadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrenador() throws Exception {
        // Initialize the database
        entrenadorRepository.saveAndFlush(entrenador);
        int databaseSizeBeforeUpdate = entrenadorRepository.findAll().size();

        // Update the entrenador
        Entrenador updatedEntrenador = entrenadorRepository.findOne(entrenador.getId());
        updatedEntrenador
                .nombre(UPDATED_NOMBRE)
                .apellido(UPDATED_APELLIDO)
                .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
                .telefono(UPDATED_TELEFONO)
                .correo(UPDATED_CORREO);

        restEntrenadorMockMvc.perform(put("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntrenador)))
            .andExpect(status().isOk());

        // Validate the Entrenador in the database
        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeUpdate);
        Entrenador testEntrenador = entrenadorList.get(entrenadorList.size() - 1);
        assertThat(testEntrenador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEntrenador.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEntrenador.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testEntrenador.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEntrenador.getCorreo()).isEqualTo(UPDATED_CORREO);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrenador() throws Exception {
        int databaseSizeBeforeUpdate = entrenadorRepository.findAll().size();

        // Create the Entrenador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntrenadorMockMvc.perform(put("/api/entrenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenador)))
            .andExpect(status().isCreated());

        // Validate the Entrenador in the database
        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntrenador() throws Exception {
        // Initialize the database
        entrenadorRepository.saveAndFlush(entrenador);
        int databaseSizeBeforeDelete = entrenadorRepository.findAll().size();

        // Get the entrenador
        restEntrenadorMockMvc.perform(delete("/api/entrenadors/{id}", entrenador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrenador> entrenadorList = entrenadorRepository.findAll();
        assertThat(entrenadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
