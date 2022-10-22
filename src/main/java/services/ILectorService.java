package services;

import java.util.List;

import entity.Lector;
import entity.Room;
import entity.Student;
import entity.StudyCourse;

public interface ILectorService {

    public List<StudyCourse> getCourses();

    public List<StudyCourse> getLectorCourses(long lector_uid);

    public StudyCourse getCourse(long course_uid);
}
