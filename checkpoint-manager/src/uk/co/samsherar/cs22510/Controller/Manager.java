package uk.co.samsherar.cs22510.Controller;

import uk.co.samsherar.cs22510.View.*;
import uk.co.samsherar.cs22510.Model.*;
import java.util.*;

/**
 * The overall manager for the whole program. It knows _everything_
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class Manager {
	/**
	 * Singleton variable
	 */
	private static Manager m = null;
	/**
	 * The frame for the GUI
	 */
	private MainFrame frame = null;
	/**
	 * List of entrants
	 */
	private LinkedList<Entrant> entrants;
	/**
	 * List of courses
	 */
	private LinkedList<Course> courses;
	/**
	 * List of checkpoints
	 */
	private LinkedList<Integer> checkpoints;
	/**
	 * Time filename for later appending
	 */
	
	private String timeFilename;
	
	/**
	 * Singleton protected Constructor
	 */
	protected Manager() {
		
	}
	
	/**
	 * Checks if the GUI is already running, and if not
	 * create it! 
	 */
	public void runGUI() {
		if(this.frame == null) {
			frame = new MainFrame();
			frame.populateEntrants(entrants);
			frame.setCourses(courses);
			frame.setCheckpoints(checkpoints);
		}
	}

	/**
	 * Parses all the data from the files inserted
	 * @param filenames the array of filenames from the commandline
	 */
	public void setFiles(String filenames[]) {
		 this.entrants = FileParser.parse_entrants(filenames[0]);
		 this.courses = FileParser.parseCourses(filenames[1]);
		 this.checkpoints = FileParser.parseCheckpoints(filenames[2]);
		 this.populateEntrantCourses();
		 FileParser.parseTimes(filenames[3], this.entrants);
		 this.timeFilename = filenames[3];
		 Entrant e = this.entrants.get(0);
	}
	
	/**
	 * Singleton method to get an instance of Manager
	 * @return Manager
	 */
	public static Manager getInstance() {
		if(m == null) {
			m = new Manager();
		}
		return m;
	}
	
	/**
	 * Links a course to the entrants
	 */
	private void populateEntrantCourses() {
		for(Entrant entrant : this.entrants) {
			Course course = null;
			for(Course c : this.courses) {
				if(entrant.getCourseID() == c.getCourseID()) {
					course = c;
					break;
				}
			}
			if(course != null) {
				entrant.setCourse(course);
			}
		}
	}
	
	/**
	 * Find entrants from their name
	 * @param name the name of the entrant
	 * @return the Entrant, null otherwise
	 */
	private Entrant findEntrant(String name) {
		Entrant ret = null;
		for(int i = 0; i < this.entrants.size(); i++) {
			if(this.entrants.get(i).getName().equals(name)) {
				ret = this.entrants.get(i);
				break;
			}
		}
		return ret;
	}
	
	/**
	 * Validates the time entered from the GUI and appends it correctly
	 * @param en the entrant
	 * @param cp the checkpoint
	 * @param arrival the arrival time
	 * @param depart the depart time (normally null)
	 * @return an error code (3 if the entrant has already been excluded)
	 * @see uk.co.samsherar.cs22510.Controller.FileParser#appendExcluded(file, cp, enID, arrival)
	 * @see uk.co.samsherar.cs22510.Controller.FileParser#appendStandard(file, cp, enID, arrival)
	 * @see uk.co.samsherar.cs22510.Controller.FileParser#appendMedicalfile, cp, enID, arrival, depart)
	 */
	public int addTime(Object en, Object cp, String arrival, String depart) {
		int ret = 0;
		Entrant entrant = this.findEntrant((String) en);
		Integer checkpoint = (Integer) cp;
		if(entrant.isExcluded()) {
			ret = 3;
		} else {
			entrant.appendVisited(checkpoint);
			if(!entrant.onPath()) {
				entrant.setExcluded(true);
				ret = FileParser.appendExcluded(this.timeFilename, checkpoint, entrant.getId(), arrival);
			} else {
				if(depart.length() == 0) {
					 ret = FileParser.appendStandard(this.timeFilename, checkpoint, entrant.getId(), arrival);
				} else {
					ret = FileParser.appendMedical(this.timeFilename, checkpoint, entrant.getId(), arrival, depart);
				}
			}
		}
		return ret;
	}
}
