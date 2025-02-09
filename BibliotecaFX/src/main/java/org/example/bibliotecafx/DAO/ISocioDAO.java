package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.entities.Socio;

import java.util.List;

public interface ISocioDAO {

    /**
     *
     * @param socio se le pasa el socio a registrar
     */

    void registrarSocio(Socio socio);

    /**
     *
     * @param socio se le pasa el socio con los datos actualizados
     */
    void modificarSocio(Socio socio);

    /**
     *
     * @param idSocio se le pasa el id del socio a buscar
     * @return devuelve el socio buscado
     */

    boolean eliminarSocio(Integer idSocio);

    /**
     *
     * @param parametro se le pasa el nombre o télefono del socio a buscar
     * @return devuelve una lista con los socios buscados
     */

    List<Socio> buscarPorParametro(String parametro);

    /**
     *
     * @return devuelve una lista con todos los socios
     */

    List<Socio> obtenerSocios();

    /**
     *
     * @param idSocio se le pasa el id del socio a buscar
     * @return devuelve el socio buscado
     */

    Socio buscarPorId(Integer idSocio);
}
