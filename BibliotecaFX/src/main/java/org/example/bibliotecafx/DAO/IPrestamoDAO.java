package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.Prestamo;

import java.util.List;

public interface IPrestamoDAO {

    /**
     *
     * @param prestamo se le pasa el prestamo a registrar
     */

    void registrarPrestamo(Prestamo prestamo);

    /**
     *
     * @param idSocio se le pasa el id del socio
     * @return devuelve una lista con todos los prestamos del socio
     */

    List<Prestamo> listarHistorialPrestamos(int idSocio);

    /**
     *
     * @return devuelve una lista con todos los libros prestados
     */

    List<Prestamo> listarLibrosPrestados();
}
