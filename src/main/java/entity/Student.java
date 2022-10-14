package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Student implements IPerson{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	String surname;
	
	@ManyToMany
	List<StudyCourse> studyCourses;

	@OneToMany(mappedBy = "student")
    List<DateEvaluation> dates;

	public Student() {
		studyCourses = new ArrayList<StudyCourse>();
		dates = new ArrayList<DateEvaluation>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setSurname(String newSurname) {
		surname = newSurname;
	}
}
