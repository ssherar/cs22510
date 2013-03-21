package uk.co.samsherar.cs22510.Controller;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import uk.co.samsherar.cs22510.Model.*;

public class FileParser {
	private static String REGEX_ENTRANT = "[0-9]+ ([A-Z]) ([a-zA-Z ]+)";
	private static String REGEX_COURSES = "([A-Z]) [0-9]+ (.*)";
	private static String REGEX_CP = "([0-9]+) CP";
	
	static {
		REGEX_ENTRANT.replace(" ", "\\ ");
		REGEX_COURSES.replace(" ", "\\ ");
		REGEX_CP.replace(" ", "\\ ");
	}
//	public static LinkedList<Entrant_Time> parse_times(String timesPath) {
//	
//	}
	
	public static LinkedList<Entrant> parse_entrants(String entrantsPath) {
		LinkedList<Entrant> ret = new LinkedList<Entrant>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(entrantsPath));
			String currentLine;
			Pattern p = Pattern.compile(REGEX_ENTRANT);
			while((currentLine = br.readLine()) != null) {
				Matcher match = p.matcher(currentLine);
				if(match.find()) {
					char course = match.group(1).toCharArray()[0];
					String name = match.group(2);
					ret.add(new Entrant(course, name));
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return ret;
	}
	
	public static LinkedList<Course> parseCourses(String coursePath) {
		LinkedList<Course> ret = new LinkedList<Course>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(coursePath));
			String currentLine;
			Pattern p = Pattern.compile(REGEX_COURSES);
			while((currentLine = br.readLine()) != null) {
				Matcher match = p.matcher(currentLine);
				if(match.find()) {
					char course = match.group(1).toCharArray()[0];
					Course c = new Course(course);
					String[] split = match.group(2).split("\\ ");
					for(int i = 0; i < split.length; i++) {
						int tmp = Integer.parseInt(split[i]);
						c.addCheckpoint(tmp);
					}
					ret.add(c);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static LinkedList<Integer> parseCheckpoints(String checkpointPath) {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(checkpointPath));
			String currentLine;
			Pattern p = Pattern.compile(REGEX_CP);
			while((currentLine = br.readLine()) != null) {
				Matcher match = p.matcher(currentLine);
				if(match.find()) {
					int tmp = Integer.parseInt(match.group(1));
					ret.add(tmp);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}
