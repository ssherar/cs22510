package uk.co.samsherar.cs22510.Model;

import java.util.*;

public class Course {
	private char courseID;
	private LinkedList<Integer> checkpoints;
	
	public Course(char courseID) {
		this.courseID = courseID;
		this.checkpoints = new LinkedList<Integer>();
	}
	
	public void addCheckpoint(int node) {
		this.checkpoints.add(node);
	}
	
	public LinkedList<Integer> getCheckpoints() {
		return this.checkpoints;
	}
	
	public char getCourseID() {
		return this.courseID;
	}
	
}
