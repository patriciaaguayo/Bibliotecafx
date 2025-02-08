package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.Util.HibernateUtil;
import org.example.bibliotecafx.entities.Autor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AutorDAOImpl implements IAutorDAO {

    /**
     * @param autor se le pasa el autor a registrar
     */
    @Override
    public void registrarAutor(Autor autor) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(autor);
            transaction.commit();
            System.out.println("\n Autor registrada exitosamente.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al registrar al autor: " + e.getMessage());
        }
    }

    /**
     * @return devuelve una lista con todos los autores
     */
    @Override
    public List<Autor> obtenerAutores() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery("FROM Autor", Autor.class).list();

        } catch (Exception e) {
            System.out.println("\n Error al obtener los autores: " + e.getMessage());
            return null;
        }
    }

    /**
     *
     *  @param idAutor se le pasa el id del autor a eliminar
     * @return devuelve true si el autor fue eliminado correctamente
     */

    @Override
    public boolean eliminarAutor(Integer idAutor) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Buscamos al autor en la base de datos
            Autor autor = session.get(Autor.class, idAutor);

            if (autor != null) {
                session.delete(autor);
                transaction.commit();
                return true;

            } else {
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al eliminar el autor: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param nombreAutor se le pasa el nombre del autor a buscar
     * @return devuelve una lista con los autores buscados
     */
    @Override
    public List<Autor> buscarPorNombre(String nombreAutor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Autor WHERE nombreAutor LIKE :nombre", Autor.class)
                    .setParameter("nombre", "%" + nombreAutor + "%") // Esto es para permitir búsqueda parcial
                    .list(); // Retorna una lista de autores
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar autor en la base de datos", e);
        }
    }

    /**
     * @param autor se le pasa el autor con los datos actualizados
     */
    @Override
    public void modificarAutor(Autor autor) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Autor autorExistente = session.get(Autor.class, autor.getIdAutor());

            if (autorExistente != null) {
                autorExistente.setNombreAutor(autor.getNombreAutor());
                autorExistente.setNacionalidad(autor.getNacionalidad());
                session.update(autorExistente);
                transaction.commit();
                System.out.println("\n Autor actualizado exitosamente.");
            } else {
                System.out.println("\n Autor con ID " + autor.getIdAutor() + " no encontrado.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al modificar el autor: " + e.getMessage());
        }
    }

    /**
     *
     * @param idAutor se le pasa el id del autor a buscar
     * @return devuelve el autor buscado
     */

    public Autor buscarPorId(Integer idAutor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Autor.class, idAutor);  // Devuelve el autor con el ID proporcionado
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el autor por ID: " + e.getMessage(), e);
        }
    }

}
