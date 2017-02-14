package com.cenfotec.escuelita.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Calificacion entity.
 */
public class CalificacionDTO implements Serializable {

    private Long id;

    private String descripcion;

    @NotNull
    private Integer nota;


    private Long jugadorId;
    

    private String jugadorNombre;

    private Long entrenamientoId;
    

    private String entrenamientoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Long getJugadorId() {
        return jugadorId;
    }

    public void setJugadorId(Long jugadorId) {
        this.jugadorId = jugadorId;
    }


    public String getJugadorNombre() {
        return jugadorNombre;
    }

    public void setJugadorNombre(String jugadorNombre) {
        this.jugadorNombre = jugadorNombre;
    }

    public Long getEntrenamientoId() {
        return entrenamientoId;
    }

    public void setEntrenamientoId(Long entrenamientoId) {
        this.entrenamientoId = entrenamientoId;
    }


    public String getEntrenamientoNombre() {
        return entrenamientoNombre;
    }

    public void setEntrenamientoNombre(String entrenamientoNombre) {
        this.entrenamientoNombre = entrenamientoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalificacionDTO calificacionDTO = (CalificacionDTO) o;

        if ( ! Objects.equals(id, calificacionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalificacionDTO{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", nota='" + nota + "'" +
            '}';
    }
}
