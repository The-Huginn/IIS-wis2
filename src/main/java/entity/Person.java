package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@ApiModel
@NamedQuery(name = "Person.checkUsername", query = "select p from Person p where p.username = :username")
public abstract class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Column(nullable = false)
	@NotNull(message = "name cannot be null [Person]")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "name must contain only valid chars [a-zA-Z] [Person]")
	@ApiModelProperty(required = true, example = "Franta")
	String name;

	@Column(nullable = false)
	@NotNull(message = "surname cannot be null [Person]")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "surname must contain only valid chars [a-zA-Z] [Person]")
	@ApiModelProperty(required = true, example = "Novak")
	String surname;

	@Column(nullable = false, unique = true)
	@NotNull(message = "username cannot be null [Person]")
	@Size(min = 5, max = 20, message = "username size must be between 5-20 chars [Person]")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", message = "username must contain only valid chars [a-zA-Z0-9] and start with char[Person]")
	@ApiModelProperty(required = true, example = "xnovak00")
	String username;

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setSurname(String newSurname) {
		surname = newSurname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String newUsername) {
		username = newUsername;
	}
}
