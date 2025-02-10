package org.example.bibliotecafx.entities;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @OneToOne
    @JoinColumn(name = "ISBN", nullable = false, unique = true)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "Id_Socio", nullable = false)
    private Socio socio;

    @Column(name = "Fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "Fecha_devolucion", nullable = true)
    private LocalDate fechaDevolucion;

    // CONSTRUCTORES

    public Prestamo() {}

    // CONSTRUCTOR CON EL QUE NO TENER PRESTAMOS ACTIVOS

    public Prestamo(Libro libro, Socio socio) {
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = LocalDate.now().minusDays(1); // Establecer la fecha de préstamo al día de ayer
        this.fechaDevolucion = LocalDate.now(); // Establecer la fecha de devolución al día de hoy
    }

    /* CONSTRUCTOR CON EL QUE TENER PRESTAMOS ACTIVOS

    public Prestamo(Libro libro, Socio socio, String hola) {
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = LocalDate.now().plusDays(7);
    }*/

    // GETTERS

    public Libro getLibro() { return libro; }

    public Socio getSocio() { return socio; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    public boolean isFinalizado() {
        return fechaDevolucion != null;
    }

    // SETTERS

    public void setLibro(Libro libro) { this.libro = libro; }

    public void setSocio(Socio socio) { this.socio = socio; }

    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    // MÉTODO PARA VALIDAR SI EL LIBRO PUEDE SER PRESTADO

    public boolean puedePrestarse() {
        return fechaDevolucion != null;
    }

    // TO STRING

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de fecha
        String estado;
        if (fechaDevolucion.isAfter(LocalDate.now())) {
            estado = "Activo";
        } else {
            estado = "Finalizado";
        }
        return "\n ISBN: " + libro.getISBN() +
                ", Título: " + libro.getTitulo() +
                ", Id Socio: " + socio.getIdSocio() +
                ", Nombre Socio: " + socio.getNombreSocio() +
                ", Fecha Préstamo: " + fechaPrestamo.format(formatter) +
                ", Fecha Devolución: " + fechaDevolucion.format(formatter) +
                ", Estado: " + estado + "\n";
    }
}