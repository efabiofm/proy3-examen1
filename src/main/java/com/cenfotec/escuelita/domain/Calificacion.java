package com.cenfotec.escuelita.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Calificacion.
 */
@Entity
@Table(name = "calificacion")
public class Calificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "nota", nullable = false)
    private Integer nota;

    @ManyToOne
    @NotNull
    private Jugador jugador;

    @ManyToOne
    @NotNull
    private Entrenamiento entrenamiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Calificacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getNota() {
        return nota;
    }

    public Calificacion nota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Calificacion jugador(Jugador jugador) {
        this.jugador = jugador;
        return this;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Entrenamiento getEntrenamiento() {
        return entrenamiento;
    }

    public Calificacion entrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
        return this;
    }

    public void setEntrenamiento(Entrenamiento entrenamiento) {
        this.entrenamiento = entrenamiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Calificacion calificacion = (Calificacion) o;
        if (calificacion.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calificacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Calificacion{" +
            "id=" + id +
            ", descripcion='" + descripcion + "'" +
            ", nota='" + nota + "'" +
            '}';
    }
}
