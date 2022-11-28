package services;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import entity.CourseDate;
import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.IGuarantService;

@Stateless
public class GuarantServiceBean extends PersonServiceBean implements IGuarantService {

    @Override
    public Lector isValidGuarant(String lectorUsername, long course_uid) {
        System.err.println(course_uid);
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", lectorUsername);
        List<Lector> reply = query.getResultList();
        if (reply.isEmpty())
            return null;
        Lector lector = reply.get(0);
        StudyCourse course = em.find(StudyCourse.class, course_uid);
        if (course == null)
            return null;
        if (course.getGuarant() != lector)
            return null;
        else
            return lector;

    }

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
    public List<StudyCourse> getGuarantCourses(String lectorUsername) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Guarant.courses", StudyCourse.class)
                .setParameter("username", lector.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public List<Student> getStudentsWithRegistration(String lectorUsername, long course_uid) {
        Lector lector = isValidGuarant(lectorUsername, course_uid);
        if (lector == null)
            return null;
        TypedQuery<Student> queryRegistration = em.createNamedQuery("Student.coursesWithRegistration", Student.class)
                .setParameter("id", course_uid);
        return queryRegistration.getResultList();
    }

    @Override
    public String addStudentToCourse(String lectorUsername, long course_uid, long student_uid) {
        Lector lector = isValidGuarant(lectorUsername, course_uid);
        if (lector == null)
            return null;
        try {
            Student student = em.find(Student.class, student_uid);
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            List<StudyCourse> coursesWithRegistration = student.getStudyCoursesHasRegistration();
            List<Student> studentsWithRegistration = course.getStudentsWithRegistration();
            if (studentsWithRegistration.contains(student) && coursesWithRegistration.contains(course)) {
                coursesWithRegistration.remove(course);
                student.setStudyCoursesHasRegistration(coursesWithRegistration);
                studentsWithRegistration.remove(student);
                course.setStudentsWithRegistration(studentsWithRegistration);
                student.addCourse(course);
                course.addStudent(student);
                em.persist(student);
                em.persist(course);
            } else
                return "Student does not have a pending registration for this study course.";
        } catch (Exception e) {
            e.printStackTrace();
            return ("Adding student failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }

    @Override
    public List<Lector> getLectors(String lectorUsername) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<Lector> query = em.createNamedQuery("Lector.getAll", Lector.class);
        return query.getResultList();
    }

    @Override
    public String addLectorToCourse(String lectorUsername, long course_uid, long lector_uid) {
        Lector lectorGuarant = isValidGuarant(lectorUsername, course_uid);
        if (lectorGuarant == null)
            return "Unauthorized.";
        try {
            Lector lector = em.find(Lector.class, lector_uid);
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            if (!course.getLectors().contains(lector)) {
                lector.addCourse(course);
                course.addLector(lector);
                em.persist(lector);
                em.persist(course);
            } else
                return "Lector already teaches this course.";
        } catch (Exception e) {
            e.printStackTrace();
            return ("Adding lector failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }

    @Override
    public List<Room> getRooms(String lectorUsername) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        TypedQuery<Room> query = em.createNamedQuery("Room.getAll", Room.class);
        return query.getResultList();
    }

    @Override
    public String createCourseDate(String lectorUsername, long course_uid, long room_uid, String description,
            String date, String time) {
        Lector lector = isValidGuarant(lectorUsername, course_uid);
        if (lector == null)
            return null;
        try {
            CourseDate newDate = new CourseDate();
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            Room room = em.find(Room.class, room_uid);
            newDate.setCourse(course);
            newDate.setRoom(room);
            newDate.setDescription(description);
            newDate.setDate(date);
            newDate.setTime(time);
            em.persist(newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Creating CourseDate failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }
        return null;
    }

    @Override
    public String updateCourseDate(String lectorUsername, long courseDate_uid, String description, String date,
            String time) {
        Lector lector = isValidLector(lectorUsername);
        if (lector == null)
            return null;
        try {
            CourseDate courseDate = em.find(CourseDate.class, courseDate_uid);
            courseDate.setDescription(description);
            courseDate.setDate(date);
            courseDate.setTime(time);
            em.persist(courseDate);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Updating CourseDate failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }
        return null;
    }

    @Override
    public String removeLectorFromCourse(String lectorUsername, long course_uid, long lector_uid) {
        Lector lectorGuarant = isValidGuarant(lectorUsername, course_uid);
        if (lectorGuarant == null)
            return "Unauthorized.";
        try {
            Lector lector = em.find(Lector.class, lector_uid);
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            if (course.getLectors().contains(lector)) {
                lector.removeCourse(course);
                course.removeLector(lector);
                em.persist(lector);
                em.persist(course);
                em.flush();
            } else
                return "Lector does not teach this course.";
        } catch (Exception e) {
            e.printStackTrace();
            return ("Removing lector failed bacause of: "
                    + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }

        return null;
    }
}
