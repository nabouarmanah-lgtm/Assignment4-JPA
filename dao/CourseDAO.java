package dao;

import config.JPAUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class CourseDAO {

    public List<Integer> getAllCoursesIds() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNativeQuery(
                    "SELECT course_id FROM courses"
            ).getResultList();
        } finally {
            em.close();
        }
    }
}