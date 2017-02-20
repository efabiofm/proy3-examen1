package com.cenfotec.escuelita.web.rest;

import com.cenfotec.escuelita.EscuelitaApp;

import com.cenfotec.escuelita.domain.Horario;
import com.cenfotec.escuelita.domain.Categoria;
import com.cenfotec.escuelita.repository.HorarioRepository;
import com.cenfotec.escuelita.service.HorarioService;
import com.cenfotec.escuelita.service.dto.HorarioDTO;
import com.cenfotec.escuelita.service.mapper.HorarioMapper;

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
 * Test class for the HorarioResource REST controller.
 *
 * @see HorarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscuelitaApp.class)
public class HorarioResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_INICIO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_INICIO = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_FIN = "AAAAAAAAAA";
    private static final String UPDATED_HORA_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_DIA = "AAAAAAAAAA";
    private static final String UPDATED_DIA = "BBBBBBBBBB";

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private HorarioMapper horarioMapper;

    @Inject
    private HorarioService horarioService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restHorarioMockMvc;

    private Horario horario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HorarioResource horarioResource = new HorarioResource();
        ReflectionTestUtils.setField(horarioResource, "horarioService", horarioService);
        this.restHorarioMockMvc = MockMvcBuilders.standaloneSetup(horarioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Horario createEntity(EntityManager em) {
        Horario horario = new Horario()
                .nombre(DEFAULT_NOMBRE)
                .horaInicio(DEFAULT_HORA_INICIO)
                .horaFin(DEFAULT_HORA_FIN)
                .dia(DEFAULT_DIA);
        // Add required entity
        Categoria categoria = CategoriaResourceIntTest.createEntity(em);
        em.persist(categoria);
        em.flush();
        horario.setCategoria(categoria);
        return horario;
    }

    @Before
    public void initTest() {
        horario = createEntity(em);
    }

    @Test
    @Transactional
    public void createHorario() throws Exception {
        int databaseSizeBeforeCreate = horarioRepository.findAll().size();

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate + 1);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testHorario.getHoraInicio()).isEqualTo(DEFAULT_HORA_INICIO);
        assertThat(testHorario.getHoraFin()).isEqualTo(DEFAULT_HORA_FIN);
        assertThat(testHorario.getDia()).isEqualTo(DEFAULT_DIA);
    }

    @Test
    @Transactional
    public void createHorarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = horarioRepository.findAll().size();

        // Create the Horario with an existing ID
        Horario existingHorario = new Horario();
        existingHorario.setId(1L);
        HorarioDTO existingHorarioDTO = horarioMapper.horarioToHorarioDTO(existingHorario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingHorarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setNombre(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setHoraInicio(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setHoraFin(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = horarioRepository.findAll().size();
        // set the field null
        horario.setDia(null);

        // Create the Horario, which fails.
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        restHorarioMockMvc.perform(post("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isBadRequest());

        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHorarios() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get all the horarioList
        restHorarioMockMvc.perform(get("/api/horarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].horaFin").value(hasItem(DEFAULT_HORA_FIN.toString())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())));
    }

    @Test
    @Transactional
    public void getHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);

        // Get the horario
        restHorarioMockMvc.perform(get("/api/horarios/{id}", horario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(horario.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.horaFin").value(DEFAULT_HORA_FIN.toString()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHorario() throws Exception {
        // Get the horario
        restHorarioMockMvc.perform(get("/api/horarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Update the horario
        Horario updatedHorario = horarioRepository.findOne(horario.getId());
        updatedHorario
                .nombre(UPDATED_NOMBRE)
                .horaInicio(UPDATED_HORA_INICIO)
                .horaFin(UPDATED_HORA_FIN)
                .dia(UPDATED_DIA);
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(updatedHorario);

        restHorarioMockMvc.perform(put("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isOk());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate);
        Horario testHorario = horarioList.get(horarioList.size() - 1);
        assertThat(testHorario.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testHorario.getHoraInicio()).isEqualTo(UPDATED_HORA_INICIO);
        assertThat(testHorario.getHoraFin()).isEqualTo(UPDATED_HORA_FIN);
        assertThat(testHorario.getDia()).isEqualTo(UPDATED_DIA);
    }

    @Test
    @Transactional
    public void updateNonExistingHorario() throws Exception {
        int databaseSizeBeforeUpdate = horarioRepository.findAll().size();

        // Create the Horario
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHorarioMockMvc.perform(put("/api/horarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(horarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Horario in the database
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHorario() throws Exception {
        // Initialize the database
        horarioRepository.saveAndFlush(horario);
        int databaseSizeBeforeDelete = horarioRepository.findAll().size();

        // Get the horario
        restHorarioMockMvc.perform(delete("/api/horarios/{id}", horario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Horario> horarioList = horarioRepository.findAll();
        assertThat(horarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
