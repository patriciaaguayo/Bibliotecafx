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

    public Prestamo(Libro libro, Socio socio, LocalDate fechaPrestamo) {
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = null;
    }

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Para formatear la fecha

        return "\n ISBN: " + libro.getISBN() +
                ", Título: " + libro.getTitulo() +
                ", Id Socio: " + socio.getIdSocio() +
                ", Socio: " + socio.getNombreSocio() +
                ", Fecha préstamo: " + fechaPrestamo.format(formatter) +
                ", Fecha devolución: " + (fechaDevolucion != null ? fechaDevolucion.format(formatter) : "Pendiente") +
                ", Estado: " + (isFinalizado() ? "Finalizado" : "Pendiente");
    }
}