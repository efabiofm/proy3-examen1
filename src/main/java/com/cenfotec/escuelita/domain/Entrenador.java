package com.cenfotec.escuelita.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Entrenador.
 */
@Entity
@Table(name = "entrenador")
public class Entrenador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @OneToMany(mappedBy = "entrenador")
    @JsonIgnore
    private Set<Entrenamiento> entrenamientos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Entrenador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Entrenador apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Entrenador fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public Entrenador telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Entrenador correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Set<Entrenamiento> getEntrenamientos() {
        return entrenamientos;
    }

    public Entrenador entrenamientos(Set<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
        return this;
    }

    public Entrenador addEntrenamiento(Entrenamiento entrenamiento) {
        entrenamientos.add(entrenamiento);
        entrenamiento.setEntrenador(this);
        return this;
    }

    public Entrenador removeEntrenamiento(Entrenamiento entrenamiento) {
        entrenamientos.remove(entrenamiento);
        entrenamiento.setEntrenador(null);
        return this;
    }

    public void setEntrenamientos(Set<Entrenamiento> entrenamientos) {
        this.entrenamientos = entrenamientos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrenador entrenador = (Entrenador) o;
        if (entrenador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrenador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entrenador{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", apellido='" + apellido + "'" +
            ", fechaNacimiento='" + fechaNacimiento + "'" +
            ", telefono='" + telefono + "'" +
            ", correo='" + correo + "'" +
            '}';
    }
}
