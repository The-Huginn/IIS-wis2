package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entity.CourseDate;
import entity.DateEvaluation;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.ILectorService;

@Stateless
public class LectorServiceBean extends PersonServiceBean implements ILectorService {

    @Override
    public List<StudyCourse> getLectorCourses(String lectorUsername) {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", lectorUsername);
        List<Lector> reply = query.getResultList();
        if (reply.isEmpty())
            return null;
        Lector lector = reply.get(0);
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Lector.courses", StudyCourse.class)
                                                    .setParameter("username", lector.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public String addEvaluation(String lectorUsername, double evaluation, long dateEvaluation_uid) {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
		query.setParameter("username", lectorUsername);
		List<Lector> reply = query.getResultList();
		if (reply.isEmpty())
			return "Unable to find Lector in this course.";
		Lector lector = reply.get(0);
		try {
			DateEvaluation dateEval = em.find(DateEvaluation.class, dateEvaluation_uid);
			dateEval.setLector(lector);
			dateEval.setEvaluation(evaluation);
			em.persist(dateEval);
		} catch (Exception e) {
			e.printStackTrace();
			return ("Adding evaluation failed bacause of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
		}

		return null;
    }

    @Override
    public List<Student> getStudentsInCourse(long course_uid) {
        TypedQuery<Student> query = em.createNamedQuery("Student.inCourse", Student.class);
        query.setParameter("id", course_uid);
        return query.getResultList();
    }

    @Override
    public List<DateEvaluation> getEvaluationsInDate(long courseDate_uid) {
        CourseDate course = em.find(CourseDate.class, courseDate_uid);
        return course.getDateEvaluations();
    }

    @Override
    public DateEvaluation getDateEvaluation(long dateEvaluation_uid) {
        return em.find(DateEvaluation.class, dateEvaluation_uid);
    }

    @Override
    public String updatePersonalInfo(String lectorUsername, Lector lectorUpdate) {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", lectorUsername);
        List<Lector> reply = query.getResultList();
        if (reply.isEmpty())
            return "Unable to find Lector.";
            Lector lector = reply.get(0);
        try {
            lector.setName(lectorUpdate.getName());
            lector.setSurname(lectorUpdate.getSurname());
            em.persist(lector);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Update personal info failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }
}
