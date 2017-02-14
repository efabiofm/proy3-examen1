package com.cenfotec.escuelita.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Horario entity.
 */
public class HorarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String horaInicio;

    @NotNull
    private String horaFin;

    @NotNull
    private String dia;


    private Long categoriaId;
    

    private String categoriaNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }


    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HorarioDTO horarioDTO = (HorarioDTO) o;

        if ( ! Objects.equals(id, horarioDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HorarioDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", dia='" + dia + "'" +
            '}';
    }
}
