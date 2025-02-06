package org.example.bibliotecafx.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @Column(name = "ISBN")
    private String ISBN;

    @Column(name = "Titulo")
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "Id_Autor")
    private Autor autor;

    @Column(name = "Editorial")
    private String editorial;

    @Column(name = "Anyo_Publicacion")
    private int anyo;

    // CONSTRUCTORES

    public Libro(){}

    public Libro(String ISBN, String titulo, Autor autor, String editorial, int anyo) {

        this.setISBN(ISBN);
        this.setTitulo(titulo);
        this.setEditorial(editorial);
        this.setAnyo(anyo);
    }

    //GETTERS

    public String getISBN() { return ISBN; }

    public String getTitulo() { return titulo; }

    public Autor getAutor() { return autor; }

    public String getEditorial() { return editorial; }

    public int getAnyo() { return anyo; }

    // SETTERS

    public void setISBN(String ISBN) { this.ISBN = ISBN.toUpperCase(); }

    public void setTitulo(String titulo) { this.titulo = capitalizarPrimeraLetra(titulo); }

    public void setEditorial(String editorial) { this.editorial = capitalizarPrimeraLetra(editorial); }

    public void setAnyo(int anyo) { this.anyo = anyo; }

    public void setAutor(Autor autor) { this.autor = autor; }

    // TO STRING

    @Override
    public String toString() {
        return "\n ISBN: " + ISBN +
                ", Título: " + titulo +
                ", Autor: " + autor.getNombreAutor() +
                ", Editorial: " + editorial +
                ", Año de publicación: " + anyo;
    }

    // MÉTODOS

    private static String capitalizarPrimeraLetra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }
        return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
    }
}
