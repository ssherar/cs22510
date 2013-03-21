package uk.co.samsherar.cs22510.Controller;

import uk.co.samsherar.cs22510.View.*;
import uk.co.samsherar.cs22510.Model.*;
import java.util.*;

public class Manager {
	private static Manager m = null;
	private MainFrame frame = null;
	private LinkedList<Entrant> entrants;
	private LinkedList<Course> courses;
	private LinkedList<Integer> checkpoints;
	protected Manager() {
		
	}

	public void runGUI() {
		if(this.frame == null) {
			frame = new MainFrame();
			frame.populateEntrants(entrants);
			frame.setCourses(courses);
			frame.setCheckpoints(checkpoints);
		}
	}

	public void setFiles(String filenames[]) {
		 this.entrants = FileParser.parse_entrants(filenames[0]);
		 this.courses = FileParser.parseCourses(filenames[1]);
		 this.checkpoints = FileParser.parseCheckpoints(filenames[2]);
	}

	public static Manager getInstance() {
		if(m == null) {
			m = new Manager();
		}
		return m;
	}
}
