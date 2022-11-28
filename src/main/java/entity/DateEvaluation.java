package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@NamedQueries({
	@NamedQuery(name = "DateEvaluation.getCourseDate", query = "select new entity.CourseDate(c.id, c.description, c.date, c.time) from DateEvaluation e join e.date c where e.id = :eval_uid")
})
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateEvaluation implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
	@XmlElement
    Student student;

    @ManyToOne(fetch=FetchType.EAGER)
	@XmlElement
    Lector lector;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JsonIgnore
    CourseDate date;

	@XmlElement
	double evaluation = 0;
	
	public DateEvaluation() {}

	public DateEvaluation(long id, double evaluation) {
		this.id = id;
		this.evaluation = evaluation;
	}
	
	public long getId() {
		return this.id;
	}

	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(Double newEvaluation) {
		evaluation = newEvaluation;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public CourseDate getDate() {
		return this.date;
	}

	public void setDate(CourseDate date) {
		this.date = date;
	}

	public Lector getLector() {
		return this.lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}
}
