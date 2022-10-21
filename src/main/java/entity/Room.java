package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Room implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

    @OneToMany(mappedBy = "room")
	@NotNull(message = "dates cannot be null [Room]")
    List<CourseDate> dates;

	@Column(nullable = false)
	@NotNull(message = "code cannot be null [Room]")
	@Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "code must contain only valid chars [a-zA-Z0-9-] [Room]")
	String code;

	@NotNull(message = "description cannot be null [Room]")
	@Pattern(regexp = "^[a-zA-Z0-9-+:!?.@#$%^&*()/<>{}]+$", message = "room must contain only valid chars [a-zA-Z0-9-+:!?.@#$%^&*()/<>{}] [Room]")
	String description;
	
	public Room() {
		dates = new ArrayList<CourseDate>();
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
}
