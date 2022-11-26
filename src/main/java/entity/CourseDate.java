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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@XmlRootElement(name = "course date")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
public class CourseDate implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    StudyCourse course;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    Room room;

    @OneToMany(mappedBy = "date")
    List<DateEvaluation> evaluations;

	@NotNull(message = "description cannot be null [CourseData]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "room must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [CourseData]")
	@ApiModelProperty(example = "This is description")
	@XmlElement
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

	public List<DateEvaluation> getDateEvaluations() {
		return this.evaluations;
	}

	public void setDateEvaluations(List<DateEvaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public void addDateEvaluation(DateEvaluation evaluation) {
		this.evaluations.add(evaluation);
	}
}
