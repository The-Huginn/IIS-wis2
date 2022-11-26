package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@NamedQueries({
	@NamedQuery(name = "Student.getAll", query = "select new entity.Student(s.id, s.name, s.surname, s.username) from Student s"),
	@NamedQuery(name = "Student.findUid", query = "select new entity.Student(s.id, s.name, s.surname, s.username) from Student s where s.username = :username"),
	@NamedQuery(name = "Student.inCourse", query = "select new entity.Student(s.id, s.name, s.surname, s.username) from Student s join s.studyCourses l where l.id = :id"),
	@NamedQuery(name = "Student.courses", query = "select new entity.Student(s.id, s.name, s.surname, s.username) from StudyCourse s join s.students l where l.username = :username")
})
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student extends Person {

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
	@XmlElement
	List<StudyCourse> studyCourses;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "studentsWithRegistration")
	@XmlElement
	List<StudyCourse> studyCoursesHasRegistration;

	@OneToMany(mappedBy = "student")
	@XmlElement
    List<DateEvaluation> dates;

	public Student() {
		super();

		studyCourses = new ArrayList<StudyCourse>();
		studyCoursesHasRegistration = new ArrayList<StudyCourse>();
		dates = new ArrayList<DateEvaluation>();
	}

	public Student(long id, String name, String surname, String username) {
		super(id, name, surname, username);
	}

	public void addCourse(StudyCourse studyCourse) {
		this.studyCourses.add(studyCourse);
	}

	public List<StudyCourse> getStudyCourses() {
		return this.studyCourses;
	}

	public void setStudyCourses(List<StudyCourse> studyCourses) {
		this.studyCourses = studyCourses;
	}

	public void addStudyCourseHasRegistration(StudyCourse studyCourse) {
		this.studyCoursesHasRegistration.add(studyCourse);
	}

	public List<StudyCourse> getStudyCoursesHasRegistration() {
		return this.studyCoursesHasRegistration;
	}

	public void setStudyCoursesHasRegistration(List<StudyCourse> studyCoursesHasRegistration) {
		this.studyCoursesHasRegistration = studyCoursesHasRegistration;
	}
}
