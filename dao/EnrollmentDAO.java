package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import models.Enrollment;

public class EnrollmentDAO {

    public List<Enrollment> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Enrollment e",
                    Enrollment.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public boolean insert(Enrollment e) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(e) FROM Enrollment e " +
                    "WHERE e.student.studentId = :sid " +
                    "AND e.courseId = :cid",
                    Long.class
            )
                    .setParameter("sid", e.getStudent().getStudentId())
                    .setParameter("cid", e.getCourseId())
                    .getSingleResult();

            if (count > 0) return false;

            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }

    public boolean update(Enrollment e) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(e);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }

    public boolean delete(Enrollment e) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Enrollment en = em.find(Enrollment.class, e.getEnrollId());
            em.remove(en);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }
        return false;
    }
}