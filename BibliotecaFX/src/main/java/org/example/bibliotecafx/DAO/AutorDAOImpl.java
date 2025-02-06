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
     * @param idAutor se le pasa el id del autor a eliminar
     */
    @Override
    public void eliminarAutor(Integer idAutor) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Autor autor = session.get(Autor.class, idAutor);

            if (autor != null) {
                session.delete(autor);
                transaction.commit();

            } else {
                throw new RuntimeException("\n Autor con ID " + idAutor + " no encontrado.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("\n Error al eliminar el autor: " + e.getMessage(), e);
        }
    }

    /**
     * @param nombreAutor se le pasa el nombre del autor a buscar
     * @return devuelve el autor buscado
     */
    @Override
    public Autor buscarPorNombre(String nombreAutor) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Autor WHERE nombreAutor = :nombre", Autor.class)
                    .setParameter("nombre", nombreAutor)
                    .uniqueResult();

        } catch (Exception e) {
            System.out.println("\n Error al buscar el autor: " + e.getMessage());
            return null;
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

}
