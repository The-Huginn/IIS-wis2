package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Student.findUid", query = "select s from Student s where s.username = :username")
public class Student extends Person{

	@ManyToMany(fetch = FetchType.LAZY)
	List<StudyCourse> studyCourses;

	@OneToMany(mappedBy = "student")
    List<DateEvaluation> dates;

	public Student() {
		studyCourses = new ArrayList<StudyCourse>();
		dates = new ArrayList<DateEvaluation>();
	}

	public void addCourse(StudyCourse course) {
		this.studyCourses.add(course);
	}
}
