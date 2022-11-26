package dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import entity.CourseDate;
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

        // general
        public StudyCourseDTO(StudyCourse course, List<CourseDate> dates, List<Student> students, List<Student> studentsWithRegistration, List<Lector> lectors) {
            this.course = course;
            this.dates = dates;
            this.students = students;
            this.studentsWithRegistration = studentsWithRegistration;
            this.lectors = lectors;
        }
        
        // admin
        public StudyCourseDTO(StudyCourse course, List<Student> students, List<Student> studentsWithRegistration, List<Lector> lectors) {
            this.course = course;
            this.students = students;
            this.studentsWithRegistration = studentsWithRegistration;
            this.lectors = lectors;
        }

        // student
        public StudyCourseDTO(StudyCourse course, List<CourseDate> dates, List<Lector> lectors) {
            this.course = course;
            this.dates = dates;
            this.lectors = lectors;
        }
    }
}
