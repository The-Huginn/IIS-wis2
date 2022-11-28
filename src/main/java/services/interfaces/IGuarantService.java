package services.interfaces;

import java.util.List;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public interface IGuarantService extends IPersonService{

    public Lector isValidGuarant(String lectorUsername, long course_uid);

    public Lector isValidLector(String lectorUsername);

    public List<StudyCourse> getGuarantCourses(String lectorUsername);

    public List<Student> getStudentsWithRegistration(String lectorUsername, long course_uid);

    public String addStudentToCourse(String lectorUsername, long course_uid, long student_uid);

    public List<Lector> getLectors(String lectorUsername);

    public String addLectorToCourse(String lectorUsername, long course_uid, long lector_uid);

    public String removeLectorFromCourse(String lectorUsername, long course_uid, long lector_uid);

    public List<Room> getRooms(String lectorUsername);

    public String createCourseDate(String lectorUsername, long course_uid, long room_uid, String description, String date, String time);

    public String updateCourseDate(String lectorUsername, long courseDate_uid, String description, String date, String time);
}
