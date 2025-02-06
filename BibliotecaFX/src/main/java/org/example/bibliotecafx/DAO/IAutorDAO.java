package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.Autor;

import java.util.List;

public interface IAutorDAO {

    /**
     *
     * @param autor se le pasa el autor a registrar
     */

    void registrarAutor(Autor autor);

    /**
     *
     * @return devuelve una lista con todos los autores
     */

    List<Autor> obtenerAutores();

    /**
     *
     * @param idAutor se le pasa el id del autor a eliminar
     */

    void eliminarAutor(Integer idAutor);

    /**
     *
     * @param nombreAutor se le pasa el nombre del autor a buscar
     * @return devuelve el autor buscado
     */

    Autor buscarPorNombre(String nombreAutor);

    /**
     *
     * @param autor se le pasa el autor con los datos actualizados
     */
    void modificarAutor(Autor autor);

}
