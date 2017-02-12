package com.cenfotec.escuelita.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Jugador.
 */
@Entity
@Table(name = "jugador")
public class Jugador implements Serializable {

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

    @Column(name = "nombre_encargado")
    private String nombreEncargado;

    @Column(name = "apellido_encargado")
    private String apellidoEncargado;

    @Column(name = "telefono_encargado")
    private String telefonoEncargado;

    @Column(name = "correo_encargado")
    private String correoEncargado;

    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Posicion posicion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Jugador nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Jugador apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Jugador fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public Jugador nombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
        return this;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getApellidoEncargado() {
        return apellidoEncargado;
    }

    public Jugador apellidoEncargado(String apellidoEncargado) {
        this.apellidoEncargado = apellidoEncargado;
        return this;
    }

    public void setApellidoEncargado(String apellidoEncargado) {
        this.apellidoEncargado = apellidoEncargado;
    }

    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }

    public Jugador telefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
        return this;
    }

    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }

    public String getCorreoEncargado() {
        return correoEncargado;
    }

    public Jugador correoEncargado(String correoEncargado) {
        this.correoEncargado = correoEncargado;
        return this;
    }

    public void setCorreoEncargado(String correoEncargado) {
        this.correoEncargado = correoEncargado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Jugador categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public Jugador posicion(Posicion posicion) {
        this.posicion = posicion;
        return this;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Jugador jugador = (Jugador) o;
        if (jugador.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Jugador{" +
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
