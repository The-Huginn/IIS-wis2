package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CourseDate implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    StudyCourse course;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    Room room;

    @OneToMany(mappedBy = "date")
    List<DateEvaluation> evaluations;

	String description;
	
	public CourseDate() {
        evaluations = new ArrayList<DateEvaluation>();
    }
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setCourse(StudyCourse course) {
		this.course = course;
	}
}
