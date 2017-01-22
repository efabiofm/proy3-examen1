package com.cenfotec.escuelita.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Horario.
 */
@Entity
@Table(name = "horario")
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private String horaFin;

    @NotNull
    @Column(name = "dia", nullable = false)
    private String dia;

    @ManyToOne
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Horario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public Horario horaInicio(String horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public Horario horaFin(String horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDia() {
        return dia;
    }

    public Horario dia(String dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Horario categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Horario horario = (Horario) o;
        if (horario.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, horario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Horario{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", horaInicio='" + horaInicio + "'" +
            ", horaFin='" + horaFin + "'" +
            ", dia='" + dia + "'" +
            '}';
    }
}
