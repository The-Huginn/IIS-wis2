package services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public class AdminServiceBean implements IAdminService {

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
    public String createLector(Lector lector) {

        try {
            em.persist(lector);
        } catch (Exception e) {
            return Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
        }

        return null;
    }

    @Override
    public String createStudent(Student student) {

        try {
            em.persist(student);
        } catch (Exception e) {
            return Optional.ofNullable(e.getMessage()).orElse("Unable to perist object");
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
        TypedQuery<StudyCourse> query = em.createQuery("select s from StudyCourse s", StudyCourse.class);
        return query.getResultList();
    }

    @Override
    public StudyCourse getCourse(long course_uid) {
        return em.find(StudyCourse.class, course_uid);
    }

    @Override
    public List<Student> getStudents() {
        TypedQuery<Student> query = em.createQuery("select s from Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public Student getStudent(long student_uid) {
        return em.find(Student.class, student_uid);
    }

    @Override
    public List<Lector> getLectors() {
        TypedQuery<Lector> query = em.createQuery("select l from Lector l", Lector.class);
        return query.getResultList();
    }

    @Override
    public Lector getLector(long lector_uid) {
        return em.find(Lector.class, lector_uid);
    }

    @Override
    public List<Room> getRooms() {
        TypedQuery<Room> query = em.createQuery("select r from Room r", Room.class);
        return query.getResultList();
    }

    @Override
    public Room getRoom(long room_uid) {
        return em.find(Room.class, room_uid);
    }

    @Override
    public List<Lector> getGuarants() {
        TypedQuery<Lector> query = em.createNamedQuery("getGuarants", Lector.class);
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
        em.remove(em.find(Lector.class, lector_uid));
        return null;
    }

    @Override
    public String removeStudent(long student_uid) {
        em.remove(em.find(Student.class, student_uid));
        return null;
    }

    @Override
    public String removeRoom(long room_uid) {
        em.remove(em.find(Room.class, room_uid));
        return null;
    }

}