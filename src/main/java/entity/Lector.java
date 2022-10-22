package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name = "Lector.findUid", query = "select l from Lector l where l.username = :username")
public class Lector extends Person{

    @OneToMany(mappedBy = "guarant")
    List<StudyCourse> coursesGuarant;

	@OneToMany(mappedBy = "lector")
    List<DateEvaluation> dates;

	@ManyToMany
    List<StudyCourse> coursesLector;

	
	public Lector() {
        coursesGuarant = new ArrayList<StudyCourse>();
		coursesLector = new ArrayList<StudyCourse>();
		dates = new ArrayList<DateEvaluation>();
    }

	public void addCourse(StudyCourse course) {
		this.coursesLector.add(course);
	}
}
