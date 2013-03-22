package uk.co.samsherar.cs22510.Model;

import java.util.*;

/**
 * The course model
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class Course {
	/**
	 * The course id
	 */
	private char courseID;
	/**
	 * List of checkpoints associated to the course id
	 */
	private LinkedList<Integer> checkpoints;
	
	/**
	 * Constructor: assigns course id and instantiates everything
	 * @param courseID
	 */
	public Course(char courseID) {
		this.courseID = courseID;
		this.checkpoints = new LinkedList<Integer>();
	}
	
	/**
	 * Adds checkpoint to the list of the nodes
	 * @param node the node id
	 */
	public void addCheckpoint(int node) {
		this.checkpoints.add(node);
	}
	
	/**
	 * Gets the list of checkpoints
	 * @return list of checkpoints
	 */
	public LinkedList<Integer> getCheckpoints() {
		return this.checkpoints;
	}
	
	/**
	 * Gets course id
	 * @return course identifier
	 */
	public char getCourseID() {
		return this.courseID;
	}
	
}
