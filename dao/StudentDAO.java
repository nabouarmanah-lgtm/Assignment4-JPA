package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;
import models.Student;

public class StudentDAO {

    public List<Integer> getAllStudentsids() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT s.studentId FROM Student s",
                    Integer.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Student findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }
}