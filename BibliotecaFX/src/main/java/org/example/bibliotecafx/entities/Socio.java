package org.example.bibliotecafx.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "socios")
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Socio", nullable = false)
    private Integer idSocio;

    @Column(name = "Nombre", nullable = false)
    private String nombreSocio;

    @Column(name = "Direccion", nullable = false)
    private String direccion;

    @Column(name = "Telefono", nullable = false)
    private String telefono;

    // CONSTRUCTORES

    public Socio() {}

    public Socio(String nombreSocio, String direccion, String telefono) {

        this.setNombreSocio(nombreSocio);
        this.setDireccion(direccion);
        this.setTelefono(telefono);
    }

    // GETTERS

    public Integer getIdSocio() { return idSocio; }

    public String getNombreSocio() { return nombreSocio; }

    public String getDireccion() { return direccion; }

    public String getTelefono() { return telefono; }

    // SETTERS

    public void setNombreSocio(String nombreSocio) { this.nombreSocio = capitalizarPrimeraLetra(nombreSocio); }

    public void setDireccion(String direccion) { this.direccion = capitalizarPrimeraLetra(direccion); }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    // TO STRING

    @Override
    public String toString() {
        return "\n Id: " + idSocio +
                ", Nombre: " + nombreSocio +
                ", Dirección: " + direccion +
                ", Teléfono: " + telefono;
    }

    // MÉTODOS

    private static String capitalizarPrimeraLetra(String palabra) {
        if (palabra == null || palabra.isEmpty()) {
            return palabra;
        }
        return palabra.substring(0, 1).toUpperCase() + palabra.substring(1).toLowerCase();
    }

}
