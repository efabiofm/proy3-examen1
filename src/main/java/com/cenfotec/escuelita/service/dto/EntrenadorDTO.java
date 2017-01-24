package com.cenfotec.escuelita.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Entrenador entity.
 */
public class EntrenadorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String apellido;

    @NotNull
    private LocalDate fechaNacimiento;

    private String telefono;

    private String correo;


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
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntrenadorDTO entrenadorDTO = (EntrenadorDTO) o;

        if ( ! Objects.equals(id, entrenadorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EntrenadorDTO{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", apellido='" + apellido + "'" +
            ", fechaNacimiento='" + fechaNacimiento + "'" +
            ", telefono='" + telefono + "'" +
            ", correo='" + correo + "'" +
            '}';
    }
}
