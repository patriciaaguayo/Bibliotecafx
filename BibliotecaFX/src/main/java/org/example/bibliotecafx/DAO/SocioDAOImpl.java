package org.example.bibliotecafx.DAO;

import org.example.bibliotecafx.Util.HibernateUtil;
import org.example.bibliotecafx.entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class SocioDAOImpl implements ISocioDAO {

    public SocioDAOImpl() {}

    /**
     * @param socio se le pasa el socio a registrar
     */

    @Override
    public void registrarSocio(Socio socio) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(socio);
            transaction.commit();
            System.out.println("\n Socio registrado exitosamente.");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al registrar al socio: " + e.getMessage());
        }
    }

    /**
     * @param socio se le pasa el socio con los datos actualizados
     */

    @Override
    public void modificarSocio(Socio socio) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Socio socioExistente = session.get(Socio.class, socio.getIdSocio());

            if (socioExistente != null) {
                socioExistente.setNombreSocio(socio.getNombreSocio());
                socioExistente.setDireccion(socio.getDireccion());
                socioExistente.setTelefono(socio.getTelefono());
                session.update(socioExistente);
                transaction.commit();
                System.out.println("\n Socio actualizado exitosamente.");
            } else {
                System.out.println("\n Socio con ID " + socio.getIdSocio() + " no encontrado.");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al modificar el socio: " + e.getMessage());
        }
    }

    /**
     * @param idSocio se le pasa el id del socio a buscar
     * @return devuelve el socio buscado
     */

    @Override
    public boolean eliminarSocio(Integer idSocio) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Socio socio = session.get(Socio.class, idSocio);

            if (socio != null) {
                session.delete(socio);
                transaction.commit();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al eliminar el socio: " + e.getMessage());
            return false;
        }
    }

    /**
     * @param parametro se le pasa el nombre o télefono del socio a buscar
     * @return devuelve una lista con los socios buscados
     */

    @Override
    public List<Socio> buscarPorParametro(String parametro) {
        Transaction transaction = null;
        List<Socio> sociosEncontrados = new ArrayList<>();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Socio s WHERE LOWER(s.nombreSocio) LIKE LOWER(:parametro) OR s.telefono LIKE :parametro";
            Query<Socio> query = session.createQuery(hql, Socio.class);
            query.setParameter("parametro", "%" + parametro + "%");
            sociosEncontrados = query.list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("\n Error al buscar socios por parámetro: " + e.getMessage());
        }
        return sociosEncontrados;
    }

    /**
     * @return devuelve una lista con todos los socios
     */

    @Override
    public List<Socio> obtenerSocios() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Socio", Socio.class).list();

        } catch (Exception e) {
            System.out.println("\n Error al obtener los socios: " + e.getMessage());
            return null;
        }
    }

    /**
     * @param idSocio se le pasa el id del socio a buscar
     * @return devuelve el socio buscado
     */

    @Override
    public Socio buscarPorId(Integer idSocio) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Socio.class, idSocio);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar el socio por ID: " + e.getMessage(), e);
        }
    }
}
