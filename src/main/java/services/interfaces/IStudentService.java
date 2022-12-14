package services.interfaces;

import java.util.List;

import dtos.Common.CourseDateDTOEval;
import entity.DateEvaluation;
import entity.Student;
import entity.StudyCourse;

public interface IStudentService extends IPersonService{

    public Student isValidStudent(String studentUsername);

    public List<DateEvaluation> getStudentEvaluations(String studentUsername, long course_uid);

    public List<CourseDateDTOEval> getStudentCourseDates(String studentUsername, long course_uid);

    public List<StudyCourse> getStudentCourses(String studentUsername);

    public List<StudyCourse> getStudentCoursesWithRegistration(String studentUsername);

    public String createDateEvaluation(String studentUsername, long courseDate_uid);

    public String createStudyCourseRegistration(String studentUsername, long course_uid);

    public String updatePersonalInfo(String studentUsername, Student studentUpdate);
}
