package services.interfaces;

import java.util.List;

import dtos.Common.CourseDateDTO;
import dtos.Common.StudyCourseDTO;
import entity.CourseDate;
import entity.StudyCourse;

public interface IPersonService {

    public List<StudyCourse> getCourses();

    public StudyCourseDTO getCourse(long course_uid);
    
    public List<CourseDate> getDatesInCourse(long course_uid);

    public CourseDateDTO getCourseDate(long courseDate_uid);
}
