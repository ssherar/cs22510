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
	private String timeFilename;
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
		 this.populateEntrantCourses();
		 FileParser.parseTimes(filenames[3], this.entrants);
		 this.timeFilename = filenames[3];
		 Entrant e = this.entrants.get(0);
	}

	public static Manager getInstance() {
		if(m == null) {
			m = new Manager();
		}
		return m;
	}
	
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
