package services.interfaces;

import java.util.List;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public interface IGuarantService extends IPersonService{

    public List<StudyCourse> getGuarantCourses(String lectorUsername);

    public List<Student> getStudentsWithRegistration(long course_uid);

    public String addStudentToCourse(long course_uid, long student_uid);

    public List<Lector> getLectors();

    public String addLectorToCourse(long course_uid, long lector_uid);

    public List<Room> getRooms();

    public String createCourseDate(long course_uid, long room_uid);
}
