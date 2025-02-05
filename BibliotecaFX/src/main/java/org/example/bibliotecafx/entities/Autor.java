package org.example.bibliotecafx.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Autor", nullable = false)
    private Integer idAutor;

    @Column(name = "Nombre_Autor", nullable = false)
    private String nombreAutor;

    @Column(name = "Nacionalidad", nullable = false)
    private String nacionalidad;

    // CONSTRUCTORES

    public Autor(){}

    public Autor(String nombreAutor, String nacionalidad) {

    }

    // GETTERS

    public Integer getIdAutor() { return idAutor; }

    public String getNombreAutor() { return nombreAutor; }

    public String getNacionalidad() { return nacionalidad; }

    // SETTERS

    public void setIdAutor(Integer idAutor) { this.idAutor = idAutor; }

    public void setNombreAutor(String nombreAutor) { this.nombreAutor = capitalizarPrimeraLetra(nombreAutor); }

    public void setNacionalidad(String nacionalidad) { this.nacionalidad = capitalizarPrimeraLetra(nacionalidad); }

    // TO STRING

    @Override
    public String toString() {
        return "\n Id: " + idAutor +
                ", Nombre: " + nombreAutor +
                ", Nacionalidad: " + nacionalidad;
    }

    // MÉTODOS

    private static String capitalizarPrimeraLetra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }
        return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
    }
}