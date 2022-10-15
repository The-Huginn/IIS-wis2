package entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String name;
	String surname;


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
}
