package uk.co.samsherar.cs22510.Controller;

import uk.co.samsherar.cs22510.View.*;

public class Manager {
	private static Manager m = null;
	private MainFrame frame = null;

	protected Manager() {
		System.out.println("Hello World");
	}

	public void runGUI() {
		if(this.frame == null) {
			frame = new MainFrame();
		}
	}

	public static Manager getInstance() {
		if(m == null) {
			m = new Manager();
		}
		return m;
	}
}
