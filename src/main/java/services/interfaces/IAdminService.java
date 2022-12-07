package services.interfaces;

import java.util.List;

import dtos.Common.LectorDTO;
import dtos.Common.RoomDTO;
import dtos.Common.StudentDTO;
import dtos.Common.StudyCourseDTO;
import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public interface IAdminService {
    
    public String createCourse(StudyCourse course);

    public String createLector(Lector lector, String password);

    public String createStudent(Student student, String password);

    public String createRoom(Room room);

    public String addGuarant(long course_uid, long guarant_uid);

    public List<StudyCourse> getCourses();

    public StudyCourseDTO getCourse(long course_uid);
    
    public List<Student> getStudents();
    
    public StudentDTO getStudent(long student_uid);

    public List<Lector> getLectors();

    public LectorDTO getLector(long lector_uid);

    public List<Room> getRooms();

    public RoomDTO getRoom(long room_uid);

    public List<Lector> getGuarants();

    public Lector getGuarant(long course_uid);
    
    public String removeCourse(long course_uid);

    public String removeLector(long lector_uid);

    public String removeStudent(long student_uid);
    
    public String removeRoom(long room_uid);
}
