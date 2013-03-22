package uk.co.samsherar.cs22510.Controller;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.regex.*;
import uk.co.samsherar.cs22510.Model.*;
/**
 * A static class which parses and writes files out.
 * @author Samuel B Sherar <sbs1@aber.ac.uk>
 */
public class FileParser {
	/**
	 * Regex for parsing the entrants file
	 */
	private static String REGEX_ENTRANT = "([0-9]+) ([A-Z]) ([a-zA-Z ]+)";
	/**
	 * Regex for parsing the courses file
	 */
	private static String REGEX_COURSES = "([A-Z]) [0-9]+ (.*)";
	/**
	 * Regex for parsing the checkpoint file, which only accepts CP, and not JN
	 */
	private static String REGEX_CP = "([0-9]+) CP";
	/**
	 * Regex for parsing the times file
	 */
	private static String REGEX_TIMES = "([A-Z]) ([0-9]+) ([0-9]+) ([0-9]+\\:[0-9]+)";
	
	/**
	 * Escapes all whitespace characters, as {@link Java.util.regex.Pattern#Pattern()}
	 * dislikes spaces for no apparent reason.
	 */
	static {
		REGEX_ENTRANT.replace(" ", "\\ ");
		REGEX_COURSES.replace(" ", "\\ ");
		REGEX_CP.replace(" ", "\\ ");
		REGEX_TIMES.replace(" ", "\\ ");
	}
	
	/**
	 * Parses entrants and returns a List of {@link uk.co.samsherar.cs22510.Model.Entrant}
	 * to be manipulated
	 * @param entrantsPath the path to the entratns file
	 * @return list of Entrants
	 */
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
					int id = Integer.parseInt(match.group(1));
					char course = match.group(2).toCharArray()[0];
					String name = match.group(3);
					ret.add(new Entrant(course, name, id));
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
	
	/**
	 * Parses courses and returns a List of {@link uk.co.samsherar.cs22510.Model.Courses}
	 * to be manipulated
	 * @param coursePath the path to the courses file
	 * @return list of courses
	 */
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
	
	/**
	 * Parses entrants and returns a List of {@link uk.co.samsherar.cs22510.Model.Entrant}
	 * to be manipulated
	 * @param checkpointPath the path to the checkpoint file
	 * @return list of checkpoints
	 */
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
	/**
	 * Parses the times file, and adds the visited node to the specified entrant
	 * @param timeFilename the filepath for the times file
	 * @param entrants the list of entrants
	 */
	public static void parseTimes(String timeFilename, LinkedList<Entrant> entrants) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(timeFilename));
			String currentLine;
			Pattern p = Pattern.compile(REGEX_TIMES);
			while((currentLine = br.readLine()) != null) {
				Matcher match = p.matcher(currentLine);
				if(match.find()) {
					char type = match.group(1).charAt(0);
					int cpID = Integer.parseInt(match.group(2));
					int entrantID = Integer.parseInt(match.group(3));
					String time = match.group(4);
					Entrant e = FileParser.findEntrant(entrants, entrantID);
					if(type == 'I') {
						e.setExcluded(true);
					} else if(e != null) {
						e.appendVisited(cpID);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds the Entrant with a specific id in a List of entrants
	 * @param entrants the list of entrants
	 * @param id the id of the entrant
	 * @return Entrant object if exists, null otherwise
	 */
	private static Entrant findEntrant(LinkedList<Entrant> entrants, int id) {
		for(int i = 0; i < entrants.size(); i++) {
			if(entrants.get(i).getId() == id) {
				return entrants.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Appends a time formatted string to the times file with the flag
	 * to say that the entrant has been excluded from the rase
	 * @param filename the times filename
	 * @param checkpoint the checkpoint in question
	 * @param entrantId the entrant id
	 * @param arrival the arrival time
	 * @return 1 if lock was unsuccessfull, 2 otherwise
	 */
	public static int appendExcluded(String filename, Integer checkpoint, int entrantId, String arrival) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			FileLock fl = fos.getChannel().lock();
			if(fl == null) {
				return 1;
			}
			FileWriter fw = new FileWriter(fos.getFD());
			StringBuilder sb = new StringBuilder();
			sb.append("I ");
			sb.append(checkpoint + " ");
			sb.append(entrantId + " ");
			sb.append(arrival + "\n");
			fw.write(sb.toString());
			fl.release();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 2;
		
	}
	
	/**
	 * Appends a time to the time file with a standard flag
	 * @param filename the time filepath
	 * @param checkpoint the checkpoint id
	 * @param entrantId the entrant id
	 * @param arrival the arrival time
	 * @return 1 if the lock failed, 0 if successful
	 */
	public static int appendStandard(String filename, Integer checkpoint,
			int entrantId, String arrival) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			FileLock fl = fos.getChannel().lock();
			if(fl == null) {
				return 1;
			}
			FileWriter fw = new FileWriter(fos.getFD());
			StringBuilder sb = new StringBuilder();
			sb.append("T ");
			sb.append(checkpoint + " ");
			sb.append(entrantId + " ");
			sb.append(arrival + "\n");
			fw.write(sb.toString());
			fl.release();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	/**
	 * Appends 2 strings to the times file, one with Arrival time, and 
	 * one with the departed time
	 * @param filename the time filename
	 * @param checkpoint the checkpoint id
	 * @param entrantId the entrant id
	 * @param arrival the arrival time
	 * @param depart the departure time
	 * @return 1 if lock failed, 0 otherwise
	 */
	public static int appendMedical(String filename, Integer checkpoint,
			int entrantId, String arrival, String depart) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true);
			FileLock fl = fos.getChannel().lock();
			if(fl == null) {
				return 1;
			}
			FileWriter fw = new FileWriter(fos.getFD());
			StringBuilder sbArrival = new StringBuilder();
			sbArrival.append("A ");
			sbArrival.append(checkpoint + " ");
			sbArrival.append(entrantId + " ");
			sbArrival.append(arrival + "\n");
			fw.write(sbArrival.toString());
			
			StringBuilder sbDepart = new StringBuilder();
			sbDepart.append("D ");
			sbDepart.append(checkpoint + " ");
			sbDepart.append(entrantId + " ");
			sbDepart.append(depart + "\n");
			fw.write(sbDepart.toString());
			fl.release();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
