package uk.co.samsherar.cs22510.Model;

import java.util.LinkedList;

/**
 * Model for the Entrants
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class Entrant {
	/**
	 * The course identifier for the entrants
	 */
	private char courseID;
	
	/**
	 * the name of the entrant
	 */
	private String name;
	
	/**
	 * the unique identifier for the entrant
	 */
	private int id;
	
	/**
	 * the list of nodes which the entrant will visit
	 */
	private Course course;
	
	/**
	 * List of visitied nodes
	 */
	private LinkedList<Integer> visited;
	
	/**
	 * Is the entrant excluded? Time will only tell...
	 */
	private boolean excluded = false;
	
	/**
	 * Constructor: Instantiates the class variables
	 * @param courseID the couse id
	 * @param name the name of the entrant
	 * @param id the identifier
	 */
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
	
	/**
	 * Compares the list of visited nodes to the course the entrant is on, 
	 * and if there is any deviation, we can exclude them
	 * @return if there is any deviation
	 */
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
