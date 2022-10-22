package services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Lector;
import entity.Person;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public class LectorServiceBean implements ILectorService {

    @PersistenceContext(unitName = "primary")
    EntityManager em;
    
    @Override
    public List<StudyCourse> getCourses() {
        TypedQuery<StudyCourse> query = em.createQuery("select s from StudyCourse s", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public List<StudyCourse> getLectorCourses(long lector_uid) {
        TypedQuery<StudyCourse> query = em.createQuery("select s from StudyCourse s where s.", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public StudyCourse getCourse(long course_uid) {
        return em.find(StudyCourse.class, course_uid);
    }


}
