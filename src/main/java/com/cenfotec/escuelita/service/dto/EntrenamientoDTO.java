package com.cenfotec.escuelita.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Entrenamiento entity.
 */
public class EntrenamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;


    private Long horarioId;
    

    private String horarioNombre;

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
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }


    public String getHorarioNombre() {
        return horarioNombre;
    }

    public void setHorarioNombre(String horarioNombre) {
        this.horarioNombre = horarioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrenamientoDTO entrenamientoDTO = (EntrenamientoDTO) o;

        if ( ! Objects.equals(id, entrenamientoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EntrenamientoDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
