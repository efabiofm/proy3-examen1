package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.*;
import com.cenfotec.escuelita.repository.EntrenadorRepository;
import com.cenfotec.escuelita.repository.EntrenamientoRepository;
import com.cenfotec.escuelita.repository.HorarioRepository;
import com.cenfotec.escuelita.repository.JugadorRepository;
import com.cenfotec.escuelita.service.dto.EntrenadorDTO;
import com.cenfotec.escuelita.service.dto.EntrenamientoDTO;
import com.cenfotec.escuelita.service.dto.HorarioDTO;
import com.cenfotec.escuelita.service.dto.JugadorDTO;
import com.cenfotec.escuelita.service.mapper.EntrenadorMapper;
import com.cenfotec.escuelita.service.mapper.EntrenamientoMapper;
import com.cenfotec.escuelita.service.mapper.HorarioMapper;
import com.cenfotec.escuelita.service.mapper.JugadorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Entrenamiento.
 */
@Service
@Transactional
public class EntrenamientoService {

    private final Logger log = LoggerFactory.getLogger(EntrenamientoService.class);

    @Inject
    private EntrenamientoRepository entrenamientoRepository;

    @Inject
    private EntrenamientoMapper entrenamientoMapper;

    @Inject
    private JugadorRepository jugadorRepository;

    @Inject
    private JugadorMapper jugadorMapper;

    @Inject
    private EntrenadorRepository entrenadorRepository;

    @Inject
    private EntrenadorMapper entrenadorMapper;

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private HorarioMapper horarioMapper;

    /**
     * Save a entrenamiento.
     *
     * @param entrenamientoDTO the entity to save
     * @return the persisted entity
     */
    public EntrenamientoDTO save(EntrenamientoDTO entrenamientoDTO) {
        log.debug("Request to save Entrenamiento : {}", entrenamientoDTO);
        Entrenamiento entrenamiento = entrenamientoMapper.entrenamientoDTOToEntrenamiento(entrenamientoDTO);
        entrenamiento = entrenamientoRepository.save(entrenamiento);
        EntrenamientoDTO result = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);
        return result;
    }

    /**
     *  Get all the entrenamientos.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<EntrenamientoDTO> findAll() {
        log.debug("Request to get all Entrenamientos");
        List<EntrenamientoDTO> result = entrenamientoRepository.findAll().stream()
            .map(entrenamientoMapper::entrenamientoToEntrenamientoDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        obtenerProxEntrenamiento(Long.valueOf(1));

        return result;
    }


    @Transactional (readOnly = true)
    public String obtenerProxEntrenamiento(Long idUsuario){

        String parametro = "";
        String result = "";
        Calendar now = Calendar.getInstance();
        String[] strDays = new String[]{
            "Domingo", "Lunes", "Martes", "Miercoles",
            "Jueves", "Viernes", "Sabado"};

        String diaDeLaSemana = strDays[now.get(Calendar.DAY_OF_WEEK) - 1];

        List<EntrenamientoDTO> lstEntrenamientos = entrenamientoRepository.findAllByEntrenador_Id(idUsuario).stream()
            .map(entrenamientoMapper::entrenamientoToEntrenamientoDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        if(lstEntrenamientos.isEmpty()){
            result = "No posee entrenamientos asignados";
        }else {
            for (EntrenamientoDTO objDto: lstEntrenamientos) {

                Horario horario = horarioRepository.findOne(objDto.getHorarioId());
                if(horario.getDia().equals(diaDeLaSemana)){

                    System.out.println("Pal pinto");
                    String nombreEntrnador = entrenadorRepository.findOne(objDto.getEntrenadorId()).getNombre();
                    Categoria categoria = horario.getCategoria();
                    List<JugadorDTO> lstJugadores = findAllByCategoria(categoria.getId());
                    int cantJugadores = lstJugadores.size();
                    if(cantJugadores > 1) {
                        parametro = "jugadores";
                    }else {
                        parametro = "jugador";
                    }
                    result = "Hola " + nombreEntrnador +", su entrenamiento mas cercano es de "+ horario.getHoraInicio() +" a " + horario.getHoraFin() + " con " +cantJugadores+ " jugadores";
                }
                else{
                    result = "No posee entrnamientos asignados para el dia de hoy";
                }
            }
        }


        return result;
    }
    @Transactional(readOnly = true)
    public List<JugadorDTO> findAllByCategoria(Long id) {
        List<JugadorDTO> result = jugadorRepository.findAllByCategoria_Id(id).stream()
            .map(jugadorMapper::jugadorToJugadorDTO)
            .collect(Collectors.toCollection(LinkedList::new));
        return result;
    }
    /**
     *  Get one entrenamiento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public EntrenamientoDTO findOne(Long id) {
        log.debug("Request to get Entrenamiento : {}", id);
        Entrenamiento entrenamiento = entrenamientoRepository.findOne(id);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.entrenamientoToEntrenamientoDTO(entrenamiento);
        return entrenamientoDTO;
    }

    /**
     *  Delete the  entrenamiento by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrenamiento : {}", id);
        entrenamientoRepository.delete(id);
    }
}
