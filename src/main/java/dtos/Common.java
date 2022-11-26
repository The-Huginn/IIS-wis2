package dtos;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import entity.CourseDate;
import entity.DateEvaluation;
import entity.Lector;
import entity.Student;
import entity.StudyCourse;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface Common {

    @XmlRootElement
    public class StudyCourseDTO {

        @XmlElement
        StudyCourse course;
        
        @XmlElement
        List<CourseDate> dates;

        @XmlElement
        List<Student> students;

        @XmlElement
        List<Student> studentsWithRegistration;

        @XmlElement
        List<Lector> lectors;

        public StudyCourseDTO(StudyCourse course) {
            this.course = course;
            this.dates = course.getDates().stream().collect(Collectors.toList());
            this.students = course.getStudents().stream().collect(Collectors.toList());
            this.studentsWithRegistration = course.getStudentsWithRegistration().stream().collect(Collectors.toList());
            this.lectors = course.getLectors().stream().collect(Collectors.toList());
        }
    }

    public class CourseDateDTO {

        @XmlElement
        CourseDate course;

        @XmlElement
        List<DateEvaluation> evaluations;

        public CourseDateDTO(CourseDate course) {
            evaluations = course.getDateEvaluations().stream().collect(Collectors.toList());
        }
    }
}
