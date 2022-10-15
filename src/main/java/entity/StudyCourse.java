package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StudyCourse implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="guarant_id")
    Lector guarant;

    @OneToMany(mappedBy = "course")
    List<CourseDate> dates;

    @ManyToMany
    List<Student> students;

    @ManyToMany
    List<Lector> lectors;


	String description;
	
	public StudyCourse() {
		dates = new ArrayList<CourseDate>();
		students = new ArrayList<Student>();
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
}
