package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@NamedQueries({
		@NamedQuery(name = "Lector.getAll", query = "select new entity.Lector(l.id, l.name, l.surname, l.username) from Lector l"),
		@NamedQuery(name = "Lector.findUid", query = "select l from Lector l where l.username = :username"),
		@NamedQuery(name = "Lector.courses", query = "select new entity.StudyCourse(s.id, s.code, s.name, s.description) from StudyCourse s join s.lectors l where l.username = :username"),
		@NamedQuery(name = "Guarant.courses", query = "select new entity.StudyCourse(s.id, s.code, s.name, s.description) from StudyCourse s join s.guarant g where g.username = :username")
})
@XmlRootElement(name = "lector")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lector extends Person {

	@OneToMany(mappedBy = "guarant")
	@JsonIgnore
	List<StudyCourse> coursesGuarant;

	@OneToMany(mappedBy = "lector")
	@JsonIgnore
	List<DateEvaluation> dates;

	@ManyToMany(mappedBy = "lectors")
	@JsonIgnore
	List<StudyCourse> coursesLector;

	public Lector() {
		super();

		coursesGuarant = new ArrayList<StudyCourse>();
		coursesLector = new ArrayList<StudyCourse>();
		dates = new ArrayList<DateEvaluation>();
		coursesGuarant.add(null);
		coursesLector.add(null);
		dates.add(null);
	}

	public Lector(long id, String name, String surname, String username) {
		super(id, name, surname, username);
	}

	public void addCourse(StudyCourse course) {
		this.coursesLector.add(course);
	}

	public void removeCourse(StudyCourse course) {
		if (this.coursesLector.contains(course)) {
			this.coursesLector.remove(course);
		}
	}

	public List<StudyCourse> getCoursesGuarant() {
		return this.coursesGuarant;
	}

	public void setCoursesGuarant(List<StudyCourse> courses) {
		this.coursesGuarant = courses;
	}

	public List<DateEvaluation> getDates() {
		return this.dates;
	}

	public void setDates(List<DateEvaluation> dates) {
		this.dates = dates;
	}

	public List<StudyCourse> getCoursesLector() {
		return this.coursesLector;
	}

	public void setCoursesLector(List<StudyCourse> courses) {
		this.coursesLector = courses;
	}
}
