package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import dtos.Common.CourseDateDTO;
import dtos.Common.StudyCourseDTO;
import entity.CourseDate;
import entity.StudyCourse;
import services.interfaces.IPersonService;

@Stateless
public class PersonServiceBean implements IPersonService {

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    @Override
    public List<StudyCourse> getCourses() {
        TypedQuery<StudyCourse> query = em.createNamedQuery("StudyCourse.getAll", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public StudyCourseDTO getCourse(long course_uid) {
        return new StudyCourseDTO(em.find(StudyCourse.class, course_uid));
    }

    @Override
    public List<CourseDate> getDatesInCourse(long course_uid) {
        TypedQuery<CourseDate> query = em.createNamedQuery("CourseDate.inCourse", CourseDate.class);
        query.setParameter("id", course_uid);
        return query.getResultList();
    }

    @Override
    public CourseDateDTO getCourseDate(long courseDate_uid) {
        return new CourseDateDTO(em.find(CourseDate.class, courseDate_uid));
    }
}