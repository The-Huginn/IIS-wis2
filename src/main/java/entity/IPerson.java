package entity;

import java.io.Serializable;

public interface IPerson extends Serializable{

    public String getName();
	
	public String getSurname();
	
	public void setName(String newName);
	
	public void setSurname(String newSurname);
}
