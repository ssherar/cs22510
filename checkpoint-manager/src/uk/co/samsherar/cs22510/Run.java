package uk.co.samsherar.cs22510;

import uk.co.samsherar.cs22510.Controller.Manager;
/**
 * Main entry point into the program
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class Run {
	/**
	 * The main method, which checks command line args and then
	 * either exits with an error with printing usage, or creates the GUI
	 * @param args the commandline arguments
	 */
	public static void main(String[] args) {
		if(args.length != 4) {
			printUsage();
			System.exit(1);
		}
		
		Manager m =  Manager.getInstance();

		m.setFiles(args);
		m.runGUI();
	}
	
	/**
	 * Prints out the usage to the commandline
	 */
	private static void printUsage() {
		System.out.println("Usage: CheckpointManager [Entrants File] [Courses File] [Checkpoints file] [Times File] ");
	}
}
