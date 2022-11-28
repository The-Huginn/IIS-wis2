package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entity.CourseDate;
import entity.DateEvaluation;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.IStudentService;

@Stateless
public class StudentServiceBean extends PersonServiceBean implements IStudentService {

    @Override
    public Student isValidStudent(String studentUsername) {
        TypedQuery<Student> query = em.createNamedQuery("Student.findUid", Student.class);
        query.setParameter("username", studentUsername);
        List<Student> reply = query.getResultList();
        if (reply.isEmpty())
            return null;
        else
            return reply.get(0);
    }

    @Override
    public List<StudyCourse> getStudentCourses(String studentUsername) {
        Student student = isValidStudent(studentUsername);
        if (student == null)
            return null;
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Student.courses", StudyCourse.class)
                .setParameter("username", student.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public List<StudyCourse> getStudentCoursesWithRegistration(String studentUsername) {
        Student student = isValidStudent(studentUsername);
        if (student == null)
            return null;
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Student.studyCoursesWithRegistration", StudyCourse.class)
                .setParameter("username", student.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public String createDateEvaluation(String studentUsername, long courseDate_uid) {
        Student student = isValidStudent(studentUsername);
        if (student == null)
            return "Unauthorized.";
        CourseDate courseDate = em.find(CourseDate.class, courseDate_uid);
        try {
            DateEvaluation dateEval = new DateEvaluation();
            dateEval.setStudent(student);
            dateEval.setDate(courseDate);
            em.persist(dateEval);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Creating date evaluation failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }

    @Override
    public String createStudyCourseRegistration(String studentUsername, long course_uid) {
        Student student = isValidStudent(studentUsername);
        if (student == null)
            return "Unauthorized.";
        StudyCourse course = em.find(StudyCourse.class, course_uid);
        try {
            if (course.getStudentsWithRegistration().contains(student)
                    || student.getStudyCoursesHasRegistration().contains(course))
                return "Student already has registration for this study course";

            student.addStudyCourseHasRegistration(course);
            course.addStudentWithRegistration(student);
            em.persist(student);
            em.persist(course);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Creating date evaluation failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }

    @Override
    public String updatePersonalInfo(String studentUsername, Student studentUpdate) {
        Student student = isValidStudent(studentUsername);
        if (student == null)
            return "Unauthorized.";
        try {
            student.setName(studentUpdate.getName());
            student.setSurname(studentUpdate.getSurname());
            em.persist(student);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Update personal info failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }
}
