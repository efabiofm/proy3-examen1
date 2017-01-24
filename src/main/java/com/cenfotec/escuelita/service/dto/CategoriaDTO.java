package com.cenfotec.escuelita.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Categoria entity.
 */
public class CategoriaDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private Integer edad;

    private String descripcion;


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
    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriaDTO categoriaDTO = (CategoriaDTO) o;

        if ( ! Objects.equals(id, categoriaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", edad='" + edad + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
