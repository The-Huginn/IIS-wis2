package dtos;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import entity.CourseDate;
import entity.DateEvaluation;
import entity.Lector;
import entity.Room;
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

    @XmlRootElement
    public class CourseDateDTO {

        @XmlElement
        CourseDate courseDate;

        @XmlElement
        List<DateEvaluation> evaluations;

        public CourseDateDTO(CourseDate courseDate) {
            this.courseDate = courseDate;
            evaluations = courseDate.getDateEvaluations().stream().collect(Collectors.toList());
        }
    }

    @XmlRootElement
    public class StudentDTO {

        @XmlElement
        Student student;

        @XmlElement
        List<StudyCourse> studyCourses;

        @XmlElement
        List<StudyCourse> studyCoursesHasRegistration;

        @XmlElement
        List<DateEvaluation> dates;

        public StudentDTO(Student student) {
            this.student = student;
            this.studyCourses = student.getStudyCourses().stream().collect(Collectors.toList());
            this.studyCoursesHasRegistration = student.getStudyCoursesHasRegistration().stream().collect(Collectors.toList());
            this.dates = student.getDates().stream().collect(Collectors.toList());
        }
    }

    @XmlRootElement
    public class LectorDTO {

        @XmlElement
        Lector lector;

        @XmlElement
        List<StudyCourse> coursesGuarant;

        @XmlElement
    	List<DateEvaluation> dates;

        @XmlElement
        List<StudyCourse> coursesLector;

        public LectorDTO(Lector lector) {
            this.lector = lector;
            this.coursesGuarant = lector.getCoursesGuarant().stream().collect(Collectors.toList());
            this.dates = lector.getDates().stream().collect(Collectors.toList());
            this.coursesLector = lector.getCoursesLector().stream().collect(Collectors.toList());
        }

    }

    @XmlRootElement
    public class RoomDTO {

        @XmlElement
        Room room;

        @XmlElement
        List<CourseDate> dates;

        public RoomDTO(Room room) {
            this.room = room;
            this.dates = room.getDates().stream().collect(Collectors.toList());
        }
    }
}
