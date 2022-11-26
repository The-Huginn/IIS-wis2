package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;

@Entity
@NamedQueries({
		@NamedQuery(name = "getGuarants", query = "select s.guarant from StudyCourse s"),
		@NamedQuery(name = "getGuarant", query = "select s.guarant from StudyCourse s where s.id = :course_uid")
})
@ApiModel
public class StudyCourse implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@ManyToOne(fetch = FetchType.LAZY)
	Lector guarant;

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	List<CourseDate> dates;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Studies", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	List<Student> students;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Registrations", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	List<Student> studentsWithRegistration;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "Teaches", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "lector_id"))
	List<Lector> lectors;

	@NotNull(message = "code cannot be null [StudyCourse]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "code must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [StudyCourse]")
	String code;

	@NotNull(message = "name cannot be null [StudyCourse]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "name must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [StudyCourse]")
	String name;

	@NotNull(message = "description cannot be null [StudyCourse]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "description must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [StudyCourse]")
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

	public String getCode() {
		return code;
	}

	public void setCode(String newCode) {
		code = newCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setGuarant(Lector guarant) {
		this.guarant = guarant;
	}

	public Lector getGuarant() {
		return this.guarant;
	}

	public void addLector(Lector lector) {
		this.lectors.add(lector);
	}

	public List<Lector> getLectors() {
		return this.lectors;
	}

	public void setLectors(List<Lector> lectors) {
		this.lectors = lectors;
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
