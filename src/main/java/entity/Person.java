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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity(name = "Person")
@Inheritance(strategy = InheritanceType.JOINED)
@ApiModel
@NamedQuery(name = "Person.checkUsername", query = "select p from Person p where p.username = :username")
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@XmlElement
	long id;

	@Column(nullable = false)
	@NotNull(message = "name cannot be null [Person]")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "name must contain only valid chars [a-zA-Z] [Person]")
	@ApiModelProperty(required = true, example = "Franta")
	@XmlElement
	String name;

	@Column(nullable = false)
	@NotNull(message = "surname cannot be null [Person]")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "surname must contain only valid chars [a-zA-Z] [Person]")
	@ApiModelProperty(required = true, example = "Novak")
	@XmlElement
	String surname;

	@Column(nullable = false, unique = true)
	@NotNull(message = "username cannot be null [Person]")
	@Size(min = 5, max = 20, message = "username size must be between 5-20 chars [Person]")
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", message = "username must contain only valid chars [a-zA-Z0-9] and start with char[Person]")
	@ApiModelProperty(required = true, example = "xnovak00")
	@XmlElement
	String username;

	// Default constructor, needed
	public Person() {};

	public Person(long id, String name, String surname, String username) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
	}

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
