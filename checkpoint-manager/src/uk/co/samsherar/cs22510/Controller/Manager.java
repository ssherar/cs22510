package uk.co.samsherar.cs22510.Controller;

import uk.co.samsherar.cs22510.View.*;
import uk.co.samsherar.cs22510.Model.*;
import java.util.*;

public class Manager {
	private static Manager m = null;
	private MainFrame frame = null;
	private LinkedList<Entrant> entrants;
	protected Manager() {
		
	}

	public void runGUI() {
		if(this.frame == null) {
			frame = new MainFrame();
			frame.populateEntrants(entrants);
		}
	}

	public void setFiles(String filenames[]) {
		 entrants = FileParser.parse_entrants(filenames[0]);
	}

	public static Manager getInstance() {
		if(m == null) {
			m = new Manager();
		}
		return m;
	}
}
