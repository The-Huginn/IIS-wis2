package services.interfaces;

import java.util.List;

import entity.DateEvaluation;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;

public interface ILectorService extends IPersonService{

    public Lector isValidLector(String lectorUsername);

    public List<StudyCourse> getLectorCourses(String lectorUsername);

    public String addEvaluation(String lectorUsername, double evaluation, long dateEval_uid);

    public List<Student> getStudentsInCourse(String lectorUsername, long course_uid);

    public List<DateEvaluation> getEvaluationsInDate(String lectorUsername, long courseDate_uid);

    public DateEvaluation getDateEvaluation(String lectorUsername, long dateEvaluation_uid);

    public String updatePersonalInfo(String lectorUsername, Lector lectorUpdate);
}
