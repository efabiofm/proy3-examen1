package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Jugador;
import com.cenfotec.escuelita.repository.JugadorRepository;

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
 * Test class for the JugadorResource REST controller.
 *
 * @see JugadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class JugadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOMBRE_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ENCARGADO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_ENCARGADO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_ENCARGADO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_ENCARGADO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_ENCARGADO = "BBBBBBBBBB";

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJugadorMockMvc;

    private Jugador jugador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JugadorResource jugadorResource = new JugadorResource();
        ReflectionTestUtils.setField(jugadorResource, "jugadorRepository", jugadorRepository);
        this.restJugadorMockMvc = MockMvcBuilders.standaloneSetup(jugadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jugador createEntity(EntityManager em) {
        Jugador jugador = new Jugador()
                .nombre(DEFAULT_NOMBRE)
                .apellido(DEFAULT_APELLIDO)
                .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
                .nombreEncargado(DEFAULT_NOMBRE_ENCARGADO)
                .apellidoEncargado(DEFAULT_APELLIDO_ENCARGADO)
                .telefonoEncargado(DEFAULT_TELEFONO_ENCARGADO)
                .correoEncargado(DEFAULT_CORREO_ENCARGADO);
        return jugador;
    }

    @Before
    public void initTest() {
        jugador = createEntity(em);
    }

    @Test
    @Transactional
    public void createJugador() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate + 1);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testJugador.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testJugador.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testJugador.getNombreEncargado()).isEqualTo(DEFAULT_NOMBRE_ENCARGADO);
        assertThat(testJugador.getApellidoEncargado()).isEqualTo(DEFAULT_APELLIDO_ENCARGADO);
        assertThat(testJugador.getTelefonoEncargado()).isEqualTo(DEFAULT_TELEFONO_ENCARGADO);
        assertThat(testJugador.getCorreoEncargado()).isEqualTo(DEFAULT_CORREO_ENCARGADO);
    }

    @Test
    @Transactional
    public void createJugadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jugadorRepository.findAll().size();

        // Create the Jugador with an existing ID
        Jugador existingJugador = new Jugador();
        existingJugador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJugador)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setNombre(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaNacimientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = jugadorRepository.findAll().size();
        // set the field null
        jugador.setFechaNacimiento(null);

        // Create the Jugador, which fails.

        restJugadorMockMvc.perform(post("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isBadRequest());

        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJugadors() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get all the jugadorList
        restJugadorMockMvc.perform(get("/api/jugadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jugador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].nombreEncargado").value(hasItem(DEFAULT_NOMBRE_ENCARGADO.toString())))
            .andExpect(jsonPath("$.[*].apellidoEncargado").value(hasItem(DEFAULT_APELLIDO_ENCARGADO.toString())))
            .andExpect(jsonPath("$.[*].telefonoEncargado").value(hasItem(DEFAULT_TELEFONO_ENCARGADO.toString())))
            .andExpect(jsonPath("$.[*].correoEncargado").value(hasItem(DEFAULT_CORREO_ENCARGADO.toString())));
    }

    @Test
    @Transactional
    public void getJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);

        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", jugador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jugador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.nombreEncargado").value(DEFAULT_NOMBRE_ENCARGADO.toString()))
            .andExpect(jsonPath("$.apellidoEncargado").value(DEFAULT_APELLIDO_ENCARGADO.toString()))
            .andExpect(jsonPath("$.telefonoEncargado").value(DEFAULT_TELEFONO_ENCARGADO.toString()))
            .andExpect(jsonPath("$.correoEncargado").value(DEFAULT_CORREO_ENCARGADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJugador() throws Exception {
        // Get the jugador
        restJugadorMockMvc.perform(get("/api/jugadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Update the jugador
        Jugador updatedJugador = jugadorRepository.findOne(jugador.getId());
        updatedJugador
                .nombre(UPDATED_NOMBRE)
                .apellido(UPDATED_APELLIDO)
                .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
                .nombreEncargado(UPDATED_NOMBRE_ENCARGADO)
                .apellidoEncargado(UPDATED_APELLIDO_ENCARGADO)
                .telefonoEncargado(UPDATED_TELEFONO_ENCARGADO)
                .correoEncargado(UPDATED_CORREO_ENCARGADO);

        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJugador)))
            .andExpect(status().isOk());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate);
        Jugador testJugador = jugadorList.get(jugadorList.size() - 1);
        assertThat(testJugador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testJugador.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testJugador.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testJugador.getNombreEncargado()).isEqualTo(UPDATED_NOMBRE_ENCARGADO);
        assertThat(testJugador.getApellidoEncargado()).isEqualTo(UPDATED_APELLIDO_ENCARGADO);
        assertThat(testJugador.getTelefonoEncargado()).isEqualTo(UPDATED_TELEFONO_ENCARGADO);
        assertThat(testJugador.getCorreoEncargado()).isEqualTo(UPDATED_CORREO_ENCARGADO);
    }

    @Test
    @Transactional
    public void updateNonExistingJugador() throws Exception {
        int databaseSizeBeforeUpdate = jugadorRepository.findAll().size();

        // Create the Jugador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJugadorMockMvc.perform(put("/api/jugadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jugador)))
            .andExpect(status().isCreated());

        // Validate the Jugador in the database
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJugador() throws Exception {
        // Initialize the database
        jugadorRepository.saveAndFlush(jugador);
        int databaseSizeBeforeDelete = jugadorRepository.findAll().size();

        // Get the jugador
        restJugadorMockMvc.perform(delete("/api/jugadors/{id}", jugador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jugador> jugadorList = jugadorRepository.findAll();
        assertThat(jugadorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
