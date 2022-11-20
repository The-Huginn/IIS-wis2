package services.interfaces;

import java.util.List;

import entity.DateEvaluation;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;

public interface ILectorService extends IPersonService{

    public List<StudyCourse> getLectorCourses(String lectorUsername);

    public String addEvaluation(String lectorUsername, double evaluation, long dateEval_uid);

    public List<Student> getStudentsInCourse(long course_uid);

    public List<DateEvaluation> getEvaluationsInDate(long courseDate_uid);

    public DateEvaluation getDateEvaluation(long dateEvaluation_uid);

    public String updatePersonalInfo(String lectorUsername, Lector lectorUpdate);
}
