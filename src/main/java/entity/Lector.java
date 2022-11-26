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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
		@NamedQuery(name = "Lector.findUid", query = "select l from Lector l where l.username = :username"),
		@NamedQuery(name = "Lector.courses", query = "select distinct(s) from StudyCourse s join s.lectors l where l.username = :username"),
		@NamedQuery(name = "Guarant.courses", query = "select distinct(s) from StudyCourse s where s.guarant.username = :username")
})
@XmlRootElement(name = "lector")
@XmlAccessorType(XmlAccessType.FIELD)
public class Lector extends Person {

	@OneToMany(mappedBy = "guarant")
	@XmlElement
	List<StudyCourse> coursesGuarant;

	@OneToMany(mappedBy = "lector")
	@XmlElement
	List<DateEvaluation> dates;

	@ManyToMany(mappedBy = "lectors")
	@XmlElement
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

	public void addCourse(StudyCourse course) {
		this.coursesLector.add(course);
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
