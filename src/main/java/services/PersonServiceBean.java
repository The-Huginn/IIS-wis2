package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.CourseDate;
import entity.StudyCourse;
import services.interfaces.IPersonService;

@Stateless
public class PersonServiceBean implements IPersonService {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    @Override
    public List<StudyCourse> getCourses() {
        TypedQuery<StudyCourse> query = em.createQuery("select s from StudyCourse s", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public StudyCourse getCourse(long course_uid) {
        return em.find(StudyCourse.class, course_uid);
    }

    @Override
    public List<CourseDate> getDatesInCourse(long course_uid) {
        StudyCourse course = em.find(StudyCourse.class, course_uid);
        return course.getDates();
    }

    @Override
    public CourseDate getCourseDate(long courseDate_uid) {
        return em.find(CourseDate.class, courseDate_uid);
    }
}