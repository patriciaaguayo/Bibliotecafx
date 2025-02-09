package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.Util.HibernateUtil;
import org.example.bibliotecafx.entities.Libro;
import org.example.bibliotecafx.entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements ILibroDAO {

   public LibroDAOImpl() {}

    /**
     * @param libro se le pasa el libro a registrar
     */

    @Override
    public void registrarLibro(Libro libro) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(libro);
            transaction.commit();
            System.out.println("Libro registrado exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al registrar el libro: " + e.getMessage());
        }
    }

    /**
     * @param libro se le pasa el libro con los datos actualizados
     */

    @Override
    public void modificarLibro(Libro libro) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Libro libroExistente = session.get(Libro.class, libro.getISBN());
            if (libroExistente != null) {
                libroExistente.setTitulo(libro.getTitulo());
                libroExistente.setAutor(libro.getAutor());
                libroExistente.setEditorial(libro.getEditorial());
                libroExistente.setAnyo(libro.getAnyo());
                session.update(libroExistente);
                transaction.commit();
                System.out.println("Libro actualizado correctamente.");
            } else {
                System.out.println("No se encontró un libro con ISBN: " + libro.getISBN());
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al modificar el libro: " + e.getMessage());
        }
    }

    /**
     * @param ISBN se le pasa el isbn del libro a eliminar
     * @return devuelve true si el libro fue eliminado correctamente
     */

    @Override
    public boolean eliminarLibro(String ISBN) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Libro libro = session.get(Libro.class, ISBN);
            if (libro != null) {
                session.delete(libro);
                transaction.commit();
                System.out.println("Libro eliminado exitosamente.");
                return true;
            } else {
                System.out.println("No se encontró un libro con ISBN: " + ISBN);
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al eliminar el libro: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param parametro se le pasa el titulo, autor o isbn del libro a buscar
     * @return devuelve una lista con los libros buscados
     */

    @Override
    public List<Libro> buscarPorParametro(String parametro) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql;

            if (isNumeric(parametro) && parametro.length() < 13) {
                hql = """
            FROM Libro l 
            WHERE l.autor.idAutor = :idParam
            """;
                Query<Libro> query = session.createQuery(hql, Libro.class);
                Integer idAutor = Integer.parseInt(parametro);
                query.setParameter("idParam", idAutor);

                List<Libro> librosEncontrados = query.getResultList();
                System.out.println("Libros encontrados por idAutor: " + librosEncontrados.size());
                return librosEncontrados;

            } else {
                hql = """
            FROM Libro l 
            WHERE l.ISBN LIKE :param 
            OR l.titulo LIKE :param 
            OR lower(l.autor.nombreAutor) LIKE lower(:param)
            """;

                Query<Libro> query = session.createQuery(hql, Libro.class);
                query.setParameter("param", "%" + parametro + "%");

                List<Libro> librosEncontrados = query.getResultList();
                System.out.println("Libros encontrados por texto: " + librosEncontrados.size());
                return librosEncontrados;
            }

        } catch (Exception e) {
            System.out.println("Error al buscar libros: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * @return devuelve una lista con todos los libros no prestados
     */

    @Override
    public List<Libro> obtenerLibrosDisponibles() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                SELECT l FROM Libro l 
                WHERE l.ISBN NOT IN (
                    SELECT p.libro.ISBN FROM Prestamo p 
                    WHERE p.fechaDevolucion IS NULL OR p.fechaDevolucion > :hoy
                )
                """;

            Query<Libro> query = session.createQuery(hql, Libro.class);
            query.setParameter("hoy", LocalDate.now());
            return query.getResultList();

        } catch (Exception e) {
            System.out.println("Error al obtener los libros disponibles: " + e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param str se le pasa el string a comprobar
     * @return devuelve true si el string es un numero
     */

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
