package com.cenfotec.escuelita.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Jugador entity.
 */
public class JugadorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String apellido;

    @NotNull
    private LocalDate fechaNacimiento;

    private String nombreEncargado;

    private String apellidoEncargado;

    private String telefonoEncargado;

    private String correoEncargado;


    private Long categoriaId;
    

    private String categoriaNombre;

    private Long posicionId;
    

    private String posicionNombre;

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
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }
    public String getApellidoEncargado() {
        return apellidoEncargado;
    }

    public void setApellidoEncargado(String apellidoEncargado) {
        this.apellidoEncargado = apellidoEncargado;
    }
    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }

    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }
    public String getCorreoEncargado() {
        return correoEncargado;
    }

    public void setCorreoEncargado(String correoEncargado) {
        this.correoEncargado = correoEncargado;
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

    public Long getPosicionId() {
        return posicionId;
    }

    public void setPosicionId(Long posicionId) {
        this.posicionId = posicionId;
    }


    public String getPosicionNombre() {
        return posicionNombre;
    }

    public void setPosicionNombre(String posicionNombre) {
        this.posicionNombre = posicionNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JugadorDTO jugadorDTO = (JugadorDTO) o;

        if ( ! Objects.equals(id, jugadorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JugadorDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", apellido='" + apellido + "'" +
            ", fechaNacimiento='" + fechaNacimiento + "'" +
            ", nombreEncargado='" + nombreEncargado + "'" +
            ", apellidoEncargado='" + apellidoEncargado + "'" +
            ", telefonoEncargado='" + telefonoEncargado + "'" +
            ", correoEncargado='" + correoEncargado + "'" +
            '}';
    }
}
