package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.Util.HibernateUtil;
import org.example.bibliotecafx.entities.Prestamo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAOImpl implements IPrestamoDAO {
    /**
     * @param prestamo se le pasa el prestamo a registrar
     */
    @Override
    public void registrarPrestamo(Prestamo prestamo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prestamo);
            transaction.commit();
            System.out.println("Préstamo registrado exitosamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error al registrar el préstamo: " + e.getMessage());
        }
    }

    /**
     * @param idSocio se le pasa el id del socio
     * @return devuelve una lista con todos los prestamos del socio
     */
    @Override
    public List<Prestamo> listarHistorialPrestamos(int idSocio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                FROM Prestamo p 
                WHERE p.socio.idSocio = :idSocio
                ORDER BY p.fechaPrestamo DESC
            """;
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            query.setParameter("idSocio", idSocio);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener el historial de préstamos del socio: " + e.getMessage());
            return null;
        }
    }

    /**
     * @return devuelve una lista con todos los libros prestados
     */
    @Override
    public List<Prestamo> listarLibrosPrestados() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
            SELECT p FROM Prestamo p 
            WHERE p.fechaDevolucion > :hoy
            """;

            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            query.setParameter("hoy", LocalDate.now());
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener préstamos activos: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
