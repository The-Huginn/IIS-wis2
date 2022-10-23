package services.interfaces;

import java.util.List;

import entity.StudyCourse;

public interface IStudentService extends IPersonService{

    public List<StudyCourse> getStudentCourses(String studentUsername);

    public String createDateEvaluation(String studentUsername, long courseDate_uid);

    public String createStudyCourseRegistration(String studentUsername, long course_uid);
}
