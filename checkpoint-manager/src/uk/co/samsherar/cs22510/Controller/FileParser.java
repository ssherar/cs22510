package uk.co.samsherar.cs22510.Controller;

import java.io.*;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.regex.*;
import uk.co.samsherar.cs22510.Model.*;

public class FileParser {
	private static String REGEX_ENTRANT = "([0-9]+) ([A-Z]) ([a-zA-Z ]+)";
	private static String REGEX_COURSES = "([A-Z]) [0-9]+ (.*)";
	private static String REGEX_CP = "([0-9]+) CP";
	private static String REGEX_TIMES = "([A-Z]) ([0-9]+) ([0-9]+) ([0-9]+\\:[0-9]+)";
	
	static {
		REGEX_ENTRANT.replace(" ", "\\ ");
		REGEX_COURSES.replace(" ", "\\ ");
		REGEX_CP.replace(" ", "\\ ");
		REGEX_TIMES.replace(" ", "\\ ");
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
	
	private static Entrant findEntrant(LinkedList<Entrant> entrants, int id) {
		for(int i = 0; i < entrants.size(); i++) {
			if(entrants.get(i).getId() == id) {
				return entrants.get(i);
			}
		}
		return null;
	}

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
