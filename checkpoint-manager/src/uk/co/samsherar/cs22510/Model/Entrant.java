package uk.co.samsherar.cs22510.Model;

import java.util.LinkedList;

public class Entrant {
	private char courseID;
	private String name;
	private int id;
	private Course course;
	private LinkedList<Integer> visited;
	private boolean excluded = false;
	
	public Entrant(char courseID, String name, int id) {
		this.courseID = courseID;
		this.name = name;
		this.visited = new LinkedList<Integer>();
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

	public boolean isExcluded() {
		return excluded;
	}

	public void setExcluded(boolean excluded) {
		this.excluded = excluded;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void appendVisited(int node) {
		this.visited.add(node);
	}
	
	public boolean onPath() {
		boolean ret = true;
		LinkedList<Integer> checkpoints = this.course.getCheckpoints();
		for(int i = 0; i < this.visited.size(); i++) {
			if(this.visited.get(i) != checkpoints.get(i)) {
				ret = false;
			}
		}
		return ret;
	}

}
