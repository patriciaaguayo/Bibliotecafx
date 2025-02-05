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
    private String idAutor;

    @Column(name = "Serie")
    private String serie;

    @Column(name = "Editorial")
    private String editorial;

    @Column(name = "Anyo_Publicacion")
    private int ano;
}
