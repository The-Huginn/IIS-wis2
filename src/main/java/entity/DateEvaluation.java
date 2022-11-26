package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateEvaluation implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@XmlElement
    Student student;

    @ManyToOne(fetch=FetchType.LAZY)
	@XmlElement
    Lector lector;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    CourseDate date;

	@XmlElement
	double evaluation = 0;
	
	public DateEvaluation() {}
	
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
