package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entity.DateEvaluation;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.ILectorService;

@Stateless
public class LectorServiceBean extends PersonServiceBean implements ILectorService {

    @Override
    public Lector isValidLector(String lectorUsername) {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", lectorUsername);
        List<Lector> reply = query.getResultList();
        if (reply.isEmpty())
            return null;
        else
            return reply.get(0);
    }

    @Override
    public List<StudyCourse> getLectorCourses(String lectorUsername) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Lector.courses", StudyCourse.class)
                .setParameter("username", lector.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public String addEvaluation(String lectorUsername, DateEvaluation dateEvaluation, long dateEvaluation_uid) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return "Unauthorized.";
        try {
            if (dateEvaluation.getEvaluation() < 0.0 || dateEvaluation.getEvaluation() > 100.0)
                return "Evaluation needs to be between 0.0 and 100.0";
            DateEvaluation dateEval = em.find(DateEvaluation.class, dateEvaluation_uid);
            dateEval.setLector(lector);
            dateEval.setEvaluation(dateEvaluation.getEvaluation());
            em.persist(dateEval);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Adding evaluation failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }

    @Override
    public List<Student> getStudentsInCourse(String lectorUsername, long course_uid) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<Student> query = em.createNamedQuery("Student.inCourse", Student.class);
        query.setParameter("id", course_uid);
        return query.getResultList();
    }

    @Override
    public List<DateEvaluation> getEvaluationsInDate(String lectorUsername, long courseDate_uid) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<DateEvaluation> query = em.createNamedQuery("DateEvaluation.inCourseDate", DateEvaluation.class);
        query.setParameter("id", courseDate_uid);
        return query.getResultList();
    }

    @Override
    public DateEvaluation getDateEvaluation(String lectorUsername, long dateEvaluation_uid) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        return em.find(DateEvaluation.class, dateEvaluation_uid);
    }

    @Override
    public String updatePersonalInfo(String lectorUsername, Lector lectorUpdate) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return "Unauthorized.";
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
