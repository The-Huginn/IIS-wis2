package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DateEvaluation implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="student_id")
    Student student;

    @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="lector_id")
    Lector lector;

    @ManyToOne(fetch=FetchType.LAZY)
    // @JoinColumn(name="room_id")
    CourseDate date;

	double evaluation;
	
	public DateEvaluation() {}
	
	public double getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(Double newEvaluation) {
		evaluation = newEvaluation;
	}
}
