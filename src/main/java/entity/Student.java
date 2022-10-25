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

@Entity
@NamedQueries({
	@NamedQuery(name = "Student.findUid", query = "select s from Student s where s.username = :username"),
	@NamedQuery(name = "Student.inCourse", query = "select distinct(s) from Student s join s.studyCourses l where l.id = :id"),
	@NamedQuery(name = "Student.courses", query = "select distinct(s) from StudyCourse s join s.students l where l.username = :username")
})
@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student extends Person{

	@ManyToMany(fetch = FetchType.LAZY)
	@XmlElement
	List<StudyCourse> studyCourses;

	@ManyToMany(fetch = FetchType.LAZY)
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
