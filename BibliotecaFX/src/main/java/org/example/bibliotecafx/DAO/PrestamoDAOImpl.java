package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.Prestamo;

import java.util.List;

public class PrestamoDAOImpl implements IPrestamoDAO {
    /**
     * @param prestamo se le pasa el prestamo a registrar
     */
    @Override
    public void registrarLibro(Prestamo prestamo) {
        
    }

    /**
     * @param idSocio se le pasa el id del socio
     * @return devuelve una lista con todos los prestamos del socio
     */
    @Override
    public List<Prestamo> listarHistorialPrestamos(int idSocio) {
        return List.of();
    }

    /**
     * @return devuelve una lista con todos los libros prestados
     */
    @Override
    public List<Prestamo> listarLibrosPrestados() {
        return List.of();
    }
}
