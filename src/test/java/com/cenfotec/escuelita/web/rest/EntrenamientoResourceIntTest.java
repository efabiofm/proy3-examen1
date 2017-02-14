package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Entrenamiento;
import com.cenfotec.escuelita.domain.Horario;
import com.cenfotec.escuelita.repository.EntrenamientoRepository;
import com.cenfotec.escuelita.service.EntrenamientoService;
import com.cenfotec.escuelita.service.dto.EntrenamientoDTO;
import com.cenfotec.escuelita.service.mapper.EntrenamientoMapper;

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

    private static final Integer DEFAULT_ENTRENADOR_ID = 1;
    private static final Integer UPDATED_ENTRENADOR_ID = 2;

    private static final String DEFAULT_ENTRENADOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTRENADOR_NAME = "BBBBBBBBBB";

    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    @Inject
    private EntrenamientoMapper entrenamientoMapper;

    @Inject
    private EntrenamientoService entrenamientoService;

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
        ReflectionTestUtils.setField(entrenamientoResource, "entrenamientoService", entrenamientoService);
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
                .descripcion(DEFAULT_DESCRIPCION)
                .entrenadorId(DEFAULT_ENTRENADOR_ID)
                .entrenadorName(DEFAULT_ENTRENADOR_NAME);
        // Add required entity
        Horario horario = HorarioResourceIntTest.createEntity(em);
        em.persist(horario);
        em.flush();
        entrenamiento.setHorario(horario);
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
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEntrenamiento.getEntrenadorId()).isEqualTo(DEFAULT_ENTRENADOR_ID);
        assertThat(testEntrenamiento.getEntrenadorName()).isEqualTo(DEFAULT_ENTRENADOR_NAME);
    }

    @Test
    @Transactional
    public void createEntrenamientoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento with an existing ID
        Entrenamiento existingEntrenamiento = new Entrenamiento();
        existingEntrenamiento.setId(1L);
        EntrenamientoDTO existingEntrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(existingEntrenamiento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEntrenamientoDTO)))
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
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        restEntrenamientoMockMvc.perform(post("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
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
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].entrenadorId").value(hasItem(DEFAULT_ENTRENADOR_ID)))
            .andExpect(jsonPath("$.[*].entrenadorName").value(hasItem(DEFAULT_ENTRENADOR_NAME.toString())));
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
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.entrenadorId").value(DEFAULT_ENTRENADOR_ID))
            .andExpect(jsonPath("$.entrenadorName").value(DEFAULT_ENTRENADOR_NAME.toString()));
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
                .descripcion(UPDATED_DESCRIPCION)
                .entrenadorId(UPDATED_ENTRENADOR_ID)
                .entrenadorName(UPDATED_ENTRENADOR_NAME);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(updatedEntrenamiento);

        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database
        List<Entrenamiento> entrenamientoList = entrenamientoRepository.findAll();
        assertThat(entrenamientoList).hasSize(databaseSizeBeforeUpdate);
        Entrenamiento testEntrenamiento = entrenamientoList.get(entrenamientoList.size() - 1);
        assertThat(testEntrenamiento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEntrenamiento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEntrenamiento.getEntrenadorId()).isEqualTo(UPDATED_ENTRENADOR_ID);
        assertThat(testEntrenamiento.getEntrenadorName()).isEqualTo(UPDATED_ENTRENADOR_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrenamiento() throws Exception {
        int databaseSizeBeforeUpdate = entrenamientoRepository.findAll().size();

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntrenamientoMockMvc.perform(put("/api/entrenamientos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrenamientoDTO)))
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
