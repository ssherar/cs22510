package uk.co.samsherar.cs22510.Model;

public class Entrant {
	private char courseID;
	private String name;
	
	public Entrant(char courseID, String name) {
		this.courseID = courseID;
		this.name = name;
	}

	public char getCourseID() {
		return courseID;
	}

	public void setCourseID(char courseID) {
		this.courseID = courseID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
