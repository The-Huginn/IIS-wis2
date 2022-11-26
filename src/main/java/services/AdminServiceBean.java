package services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Lector;
import entity.Person;
import entity.Room;
import entity.Student;
import entity.StudyCourse;
import services.interfaces.IAdminSecurityRealm;
import services.interfaces.IAdminService;

@Stateless
public class AdminServiceBean implements IAdminService {

    @Inject
    IAdminSecurityRealm adminSecurityService;

    @PersistenceContext(unitName = "primary")
    EntityManager em;

    @Override
    public String createCourse(StudyCourse course) {

        try {
            em.persist(course);
        } catch (Exception e) {
            return Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public String createLector(Lector lector, String password) {

        String reply = adminSecurityService.addUser(lector.getUsername(), password);
        if (reply != null)
            return reply;

        reply = adminSecurityService.addRoles(lector.getUsername(), Arrays.asList("lector"));
        if (reply != null) {
            adminSecurityService.removeUser(lector.getUsername());
            return "User was removed form security realm becuase of: " + reply;
        }

        try {
            TypedQuery<Person> query = em.createNamedQuery("Person.checkUsername", Person.class);
            if (!query.setParameter("username", lector.getUsername()).getResultList().isEmpty())
                return "User with this username already exists";

            em.persist(lector);
        } catch (Exception e) {
            adminSecurityService.removeUser(lector.getUsername());
            return "User was removed from the security realm as well, because of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public String createStudent(Student student, String password) {

        String reply = adminSecurityService.addUser(student.getUsername(), password);
        if (reply != null)
            return reply;

        reply = adminSecurityService.addRoles(student.getUsername(), Arrays.asList("student"));
        if (reply != null) {
            adminSecurityService.removeUser(student.getUsername());
            return "User was removed from security realm because of: " + reply;
        }

        try {
            TypedQuery<Person> query = em.createNamedQuery("Person.checkUsername", Person.class);
            if (!query.setParameter("username", student.getUsername()).getResultList().isEmpty())
                return "User with this username already exists";

            em.persist(student);
        } catch (Exception e) {
            adminSecurityService.removeUser(student.getUsername());
            return "User was removed from the security realm as well, because of: " + Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public String createRoom(Room room) {

        try {
            em.persist(room);
        } catch (Exception e) {
            return Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public String addGuarant(long course_uid, long guarant_uid) {

        try {
            StudyCourse course = em.find(StudyCourse.class, course_uid);
            Lector lector = em.find(Lector.class, guarant_uid);
            course.setGuarant(lector);
            em.persist(course);
        } catch (Exception e) {
            return Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public List<StudyCourse> getCourses() {
        TypedQuery<StudyCourse> query = em.createNamedQuery("StudyCourse.getAll", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public StudyCourse getCourse(long course_uid) {
        // TypedQuery<Student> query1 = em.createQuery("select new entity.Student(s.id, s.name, s.surname, s.username) from Student s join s.studyCourses sc where sc.id = :id", Student.class);
        // TypedQuery<StudyCourse> query2 = em.createQuery("select new entity.StudyCourse(s.id, s.code, s.name, s.description) from StudyCourse s where s.id = :id", StudyCourse.class);
        // return query.getSingleResult();
        return null;
    }

    @Override
    public List<Student> getStudents() {
        TypedQuery<Student> query = em.createNamedQuery("Student.getAll", Student.class);
        return query.getResultList();
    }

    @Override
    public Student getStudent(long student_uid) {
        return em.find(Student.class, student_uid);
    }

    @Override
    public List<Lector> getLectors() {
        TypedQuery<Lector> query = em.createNamedQuery("Lector.getAll", Lector.class);
        return query.getResultList();
    }

    @Override
    public Lector getLector(long lector_uid) {
        return em.find(Lector.class, lector_uid);
    }

    @Override
    public List<Room> getRooms() {
        TypedQuery<Room> query = em.createNamedQuery("Room.getAll", Room.class);
        return query.getResultList();
    }

    @Override
    public Room getRoom(long room_uid) {
        return em.find(Room.class, room_uid);
    }

    @Override
    public List<Lector> getGuarants() {
        TypedQuery<Lector> query = em.createNamedQuery("StudyCourse.getGuarants", Lector.class);
        return query.getResultList();
    }

    @Override
    public Lector getGuarant(long course_uid) {
        TypedQuery<Lector> query = em.createNamedQuery("getGuarant", Lector.class);
        return query.setParameter("course_uid", course_uid).getSingleResult();
    }

    @Override
    public String removeCourse(long course_uid) {
        em.remove(em.find(StudyCourse.class, course_uid));
        return null;
    }

    @Override
    public String removeLector(long lector_uid) {

        Lector toRemove = em.find(Lector.class, lector_uid);
        adminSecurityService.removeUser(toRemove.getUsername());

        em.remove(toRemove);
        return null;
    }

    @Override
    public String removeStudent(long student_uid) {

        Student toRemove = em.find(Student.class, student_uid);
        adminSecurityService.removeUser(toRemove.getUsername());
        
        em.remove(toRemove);
        return null;
    }

    @Override
    public String removeRoom(long room_uid) {
        em.remove(em.find(Room.class, room_uid));
        return null;
    }

}
