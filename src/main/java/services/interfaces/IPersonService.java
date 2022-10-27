package services.interfaces;

import java.util.List;

import entity.CourseDate;
import entity.StudyCourse;

public interface IPersonService {

    public List<StudyCourse> getCourses();

    public StudyCourse getCourse(long course_uid);
    
    public List<CourseDate> getDatesInCourse(long course_uid);

    public CourseDate getCourseDate(long courseDate_uid);
}