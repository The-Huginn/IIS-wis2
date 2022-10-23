package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.CourseDate;
import entity.DateEvaluation;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.ILectorService;

public class LectorServiceBean implements ILectorService {

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CourseDate getCourseDate(long courseDate_uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<StudyCourse> getLectorCourses(String lectorUsername) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addEvaluation(String lectorUsername, double evaluation, long dateEval_uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Student> getStudentsInCourse(long course_uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DateEvaluation> getEvaluationsInDate(long courseDate_uid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DateEvaluation getDateEvaluation(long dateEvaluation_uid) {
        // TODO Auto-generated method stub
        return null;
    }


}
