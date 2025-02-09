package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.Libro;

import java.util.List;

public interface ILibroDAO {

    /**
     *
     * @param libro se le pasa el libro a registrar
     */

    void registrarLibro(Libro libro);

    /**
     *
     * @param libro se le pasa el libro con los datos actualizados
     */
    void modificarLibro(Libro libro);

    /**
     *
     *  @param ISBN se le pasa el isbn del libro a eliminar
     * @return devuelve true si el libro fue eliminado correctamente
     */

    boolean eliminarLibro(String ISBN);

    /**
     *
     * @param parametro se le pasa el titulo, autor o isbn del libro a buscar
     * @return devuelve una lista con los libros buscados
     */

    List<Libro> buscarPorParametro(String parametro);


    /**
     *
     * @return devuelve una lista con todos los libros no prestados
     */

    List<Libro> obtenerLibrosDisponibles();

    /**
     *
     * @param ISBN se le pasa el ISBN del libro a buscar
     * @return devuelve el libro si se encuentra, de lo contrario, retorna null
     */
    Libro buscarPorISBN(String ISBN);
}
