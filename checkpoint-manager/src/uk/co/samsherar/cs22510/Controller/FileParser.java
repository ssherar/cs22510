package uk.co.samsherar.cs22510.Controller;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import uk.co.samsherar.cs22510.Model.*;

public class FileParser {
	private static String REGEX_ENTRANT = "[0-9]+ ([A-Z]) ([a-zA-Z ]+)";
	
	static {
		REGEX_ENTRANT.replace(" ", "\\ ");
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
	
}
