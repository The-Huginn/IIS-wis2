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
    public List<StudyCourse> getGuarantCourses(String lectorUsername) {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.findUid", Lector.class);
        query.setParameter("username", lectorUsername);
        List<Lector> reply = query.getResultList();
        if (reply.isEmpty())
            return null;
        Lector lector = reply.get(0);
        TypedQuery<StudyCourse> queryCourses = em.createNamedQuery("Guarant.courses", StudyCourse.class)
                                                    .setParameter("username", lector.getUsername());
        return queryCourses.getResultList();
    }

    @Override
    public List<Student> getStudentsWithRegistration(long course_uid) {
        StudyCourse course = em.find(StudyCourse.class, course_uid);
        return course.getStudentsWithRegistration();
    }

    @Override
    public String addStudentToCourse(long course_uid, long student_uid) {
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
            }
            else return "Student does not have a pending registration for this study course.";
		} catch (Exception e) {
			e.printStackTrace();
			return ("Adding student failed bacause of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
		}

        return null;
    }

    @Override
    public List<Lector> getLectors() {
        TypedQuery<Lector> query = em.createQuery("select l from Lector l", Lector.class);
        return query.getResultList();
    }

    @Override
    public String addLectorToCourse(long course_uid, long lector_uid) {
        try {
			Lector lector = em.find(Lector.class, lector_uid);
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            if (!course.getLectors().contains(lector)) {
                lector.addCourse(course);
                course.addLector(lector);
                em.persist(lector);
                em.persist(course);
            }
            else return "Lector already teaches this course.";
		} catch (Exception e) {
			e.printStackTrace();
			return ("Adding lector failed bacause of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
		}

        return null;
    }

    @Override
    public List<Room> getRooms() {
        TypedQuery<Room> query = em.createQuery("select r from Room r", Room.class);
        return query.getResultList();
    }

    @Override
    public String createCourseDate(long course_uid, long room_uid) {
        try {
            CourseDate date = new CourseDate();
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            Room room = em.find(Room.class, room_uid);
            date.setCourse(course);
            date.setRoom(room);
            em.persist(date);
        } catch (Exception e) {
            e.printStackTrace();
            return ("Adding lector failed bacause of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object"));
        }   
        return null;
    }
    
}
