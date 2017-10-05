package ua.nure.baranov;

import java.util.Date;

/**
 * @author effffgen
 *
 */
public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDay;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public String getFullName() throws IllegalStateException{
		StringBuilder fullName=new StringBuilder();
		if(firstName==null||lastName==null) {
			throw new IllegalStateException("something is missing");
		}
		fullName.append(firstName).append(' ').append(lastName);
		return fullName.toString();
	}
	
}
