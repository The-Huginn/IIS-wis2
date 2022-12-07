package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel
@NamedQueries(
	@NamedQuery(name = "Room.getAll", query = "select new entity.Room(r.id, r.code, r.description) from Room r")
)
@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

    @OneToMany(mappedBy = "room")
	@NotNull(message = "dates cannot be null [Room]")
	@JsonIgnore
    List<CourseDate> dates;

	@Column(nullable = false)
	@NotNull(message = "code cannot be null [Room]")
	@Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "code must contain only valid chars [a-zA-Z0-9-] [Room]")
	@ApiModelProperty(required = true, example = "D-105")
	@XmlElement
	String code;

	@NotNull(message = "description cannot be null [Room]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{} ]+$", message = "room must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [Room]")
	@ApiModelProperty(example = "This is description")
	@XmlElement
	String description;
	
	public Room() {
		dates = new ArrayList<CourseDate>();
	}

	public Room(long id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}

	public List<CourseDate> getDates() {
		return this.dates;
	}

	public void setDates(List<CourseDate> dates) {
		this.dates = dates;
	}
}
