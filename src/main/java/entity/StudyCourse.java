package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@NamedQueries({
	@NamedQuery(name = "getGuarants", query = "select s.guarant from StudyCourse s"),
	@NamedQuery(name = "getGuarant", query = "select s.guarant from StudyCourse s where s.id = :course_uid")
})
public class StudyCourse implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @ManyToOne
    Lector guarant;

    @OneToMany(mappedBy = "course")
    List<CourseDate> dates;

    @ManyToMany
    List<Student> students;

	@ManyToMany
    List<Student> studentsWithRegistration;

    @ManyToMany
    List<Lector> lectors;

	@NotNull(message = "description cannot be null [StudyCourse]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{}]+$", message = "description must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [StudyCourse]")
	String description;
	
	public StudyCourse() {
		dates = new ArrayList<CourseDate>();
		students = new ArrayList<Student>();
		studentsWithRegistration = new ArrayList<Student>();
		lectors = new ArrayList<Lector>();
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void setGuarant(Lector guarant) {
		this.guarant = guarant;
	}

	public void addLector(Lector lector) {
		this.lectors.add(lector);
	}

	public void addStudent(Student student) {
		this.students.add(student);
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setStudentsWithRegistration(List<Student> studentsWithRegistration) {
		this.studentsWithRegistration = studentsWithRegistration;
	}

	public void addStudentWithRegistration(Student studentWithRegistration) {
		this.studentsWithRegistration.add(studentWithRegistration);
	}

	public List<Student> getStudentsWithRegistration() {
		return this.studentsWithRegistration;
	}

	public List<CourseDate> getDates() {
		return this.dates;
	}

	public void setDates(List<CourseDate> dates) {
		this.dates = dates;
	}

	public void addDate(CourseDate date) {
		this.dates.add(date);
	}
}
