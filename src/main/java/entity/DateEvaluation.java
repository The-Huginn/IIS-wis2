package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DateEvaluation implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    Student student;

    @ManyToOne(fetch=FetchType.LAZY)
    Lector lector;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    CourseDate date;

	double evaluation;
	
	public DateEvaluation() {}
	
	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(Double newEvaluation) {
		evaluation = newEvaluation;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setCourseDate(CourseDate date) {
		this.date = date;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}
}
