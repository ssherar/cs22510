package uk.co.samsherar.cs22510;

import uk.co.samsherar.cs22510.Controller.Manager;

public class Run {
	public static void main(String[] args) {
		if(args.length != 4) {
			printUsage();
			System.exit(1);
		}
		
		Manager m =  Manager.getInstance();

		m.setFiles(args);
		m.runGUI();
	}

	private static void printUsage() {
		System.out.println("Usage: CheckpointManager [Event Info File] [Course File] [Nodes File] [Entrant File] ");
	}
}
