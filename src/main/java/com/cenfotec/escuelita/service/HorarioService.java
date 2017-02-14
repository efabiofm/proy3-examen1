package com.cenfotec.escuelita.service;

import com.cenfotec.escuelita.domain.Horario;
import com.cenfotec.escuelita.repository.HorarioRepository;
import com.cenfotec.escuelita.service.dto.HorarioDTO;
import com.cenfotec.escuelita.service.mapper.HorarioMapper;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
<<<<<<< HEAD
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
=======
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
>>>>>>> cb0cae3b556143fea6f9e4e2033bfff3170b5285
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Horario.
 */
@Service
@Transactional
public class HorarioService {

    private final Logger log = LoggerFactory.getLogger(HorarioService.class);

    @Inject
    private HorarioRepository horarioRepository;

    @Inject
    private HorarioMapper horarioMapper;

    private HorarioDTO objDTO;

    private long idHorario;

    public long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(long idHorario) {
        this.idHorario = idHorario;
    }


    public HorarioDTO getObjDTO() {
        return objDTO;
    }

    public void setObjDTO(HorarioDTO objDTO) {
        this.objDTO = objDTO;
    }
    /**
     * Save a horario.
     *
     * @param horarioDTO the entity to save
     * @return the persisted entity
     */
    public HorarioDTO save(HorarioDTO horarioDTO) {

        HorarioDTO result = new HorarioDTO();

        if(validate(horarioDTO).equals("validado")) {
            log.debug("Request to save Horario : {}", horarioDTO);
            Horario horario = horarioMapper.horarioDTOToHorario(horarioDTO);
            horario = horarioRepository.save(horario);
            result = horarioMapper.horarioToHorarioDTO(horario);

        }else{
            if(!result.getId().equals(-1)) {
                result.setId(-1L);
            }
        }

        return result;
    }

    /**
     * Validar a horario.
     *
     * @param horarioDTO the entity to validate
     * @return the persisted entity
     */
    public String validate(HorarioDTO horarioDTO) {
//        LocalDate date = LocalDate.now();
//        DayOfWeek dow = date.getDayOfWeek();
//        String diaSemana = dow.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
//        System.out.println("Dia semana: " + diaSemana);
        log.debug("Request to validate Horario : {}", horarioDTO);
        Horario horario = horarioMapper.horarioDTOToHorario(horarioDTO);
        String resul = "";
        String horarioNuevo = horario.getDia() + "-" + horario.getHoraInicio() + "-"  + horario.getHoraFin();
//        System.out.println("horario nuevo: " +horarioNuevo);
        for (int i = 0; i < findAll().size(); i++) {
            String horarioViejo;
            HorarioDTO objH = findAll().get(i);
            horarioViejo = objH.getDia() + "-" + objH.getHoraInicio() + "-" + objH.getHoraFin();
//            System.out.println(horarioViejo);
            if (horarioNuevo.equals(horarioViejo)){
                resul = "Horario ya existe.";
                setIdHorario(objH.getId());
//               System.out.println(horarioViejo);
                break;
            }else{
                resul = "validado";
            }

        }
        return resul;
    }




    /**
     *  Get all the horarios.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<HorarioDTO> findAll() {
        log.debug("Request to get all Horarios");
        List<HorarioDTO> result = horarioRepository.findAll().stream()
            .map(horarioMapper::horarioToHorarioDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one horario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public HorarioDTO findOne(Long id) {
        log.debug("Request to get Horario : {}", id);
        Horario horario = horarioRepository.findOne(id);
        HorarioDTO horarioDTO = horarioMapper.horarioToHorarioDTO(horario);
        return horarioDTO;
    }

    /**
     *  Delete the  horario by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Horario : {}", id);
        horarioRepository.delete(id);
    }
}
