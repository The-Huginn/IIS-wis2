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
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@NamedQueries({
	@NamedQuery(name = "CourseDate.inCourse", query = "select new entity.CourseDate(l.id, l.description, l.date, l.time) from CourseDate l join l.course d where d.id = :id")
})
@XmlRootElement(name = "course date")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDate implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
    StudyCourse course;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
	@XmlElement
    Room room;

    @OneToMany(mappedBy = "date", fetch = FetchType.EAGER)
	@JsonIgnore
    List<DateEvaluation> evaluations;

	@NotNull(message = "description cannot be null [CourseData]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "description must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [CourseDate]")
	@ApiModelProperty(example = "This is description")
	@XmlElement
	String description;
	
	@NotNull(message = "date cannot be null [CourseData]")
	@Pattern(regexp = "^[0-9][0-9]?.[0-9][0-9]?.[0-9]{4}$", message = "date must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [CourseDate]")
	@ApiModelProperty(example = "1.1.2023")
	@XmlElement
	String date;	
	
	@NotNull(message = "time cannot be null [CourseData]")
	@Pattern(regexp = "^[0-9][0-9]?:[0-9][0-9]$", message = "time must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [CourseDate]")
	@ApiModelProperty(example = "9:00")
	@XmlElement
	String time;

	public CourseDate() {
        evaluations = new ArrayList<DateEvaluation>();
    }

	public CourseDate(long id, String description, String date, String time) {
		this.id = id;
		this.description = description;
		this.date = date;
		this.time = time;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String newDate) {
		this.date = newDate;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String newTime) {
		this.time = newTime;
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
