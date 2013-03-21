#include <iostream>
#include <vector>
#include "Event.h"
#include "List.h"
#include "Competitors.h"
#include "CLI.h"
#include "FileWriter.h"

using namespace std;

void event_menu();
void entrants_menu();
void courses_menu();
void print_files();

CLI cli;
Event * event;
FileWriter * file_io;
vector<Competitor> competitors;
vector<Course> courses;

int main(int argc, char** argv) {
	char choice = 'A';
	do {
		cli.write_main_menu();
		choice = cli.get_input("");
		switch(choice) {
			case '1':
				event_menu();
				break;
			case '2':
				entrants_menu();
				break;
			case '3':
				courses_menu();
				break;
			case '4':
				print_files();
				break;
		}
	} while(choice != 'q');
}

/**
 * Handles all the submenus for the event, including
 * creating and showing the current created event
 */ 
void event_menu() {
	char e_choice = 'A';
	do {
		cli.write_event_menu();
		e_choice = cli.get_input("");
		if('1' == e_choice) {
			string name = cli.get_input_string("Please enter the event name");
			int day = cli.get_input_int("Please enter a day");
			int month = cli.get_input_int("Please enter a month");
			int year = cli.get_input_int("Please enter a year");
			int hour = cli.get_input_int("Please enter the start hour");
			int minute = cli.get_input_int("Please enter the start minute");
			event = new Event(name, day, month, year, hour, minute);
		} else if('2' == e_choice) {
			if(NULL == event) {
				cli.write_screen("No event has beem created");
			} else {
				cli.write_screen(event->format_for_screen());
			}
		}
	} while(e_choice != 'q');
}

/**
 * Handles all the submenus for the entrants, including
 * Adding entrants and listing alrady created entrants
 */
void entrants_menu() {
	char en_choice = 'A';
	do {
		cli.write_entrant_menu();
		en_choice = cli.get_input("");
		if('1' == en_choice) {
			string name = cli.get_input_string("Please enter the competitor name");
			char course_id = cli.get_input("Please enter the course id");
			Competitor tmp (course_id, name);
			competitors.push_back(tmp);	
		} else if('2' == en_choice) {
			for(unsigned int i = 0; i < competitors.size(); i++) {
				char formatted[100];
				Competitor tmp = competitors[i];
				sprintf(formatted, "%02d \t %c \t",
							i + 1,
							tmp.course_id);
				cout << formatted << tmp.name << endl;
			}
		}
	} while(en_choice != 'q');
}

/**
 * Handles all the submenus for the courses, including
 * creating and listing of current courses created.
 */
void courses_menu() {
	char c_choice = 'A';
	do {
		cli.write_courses_menu();
		c_choice = cli.get_input("");
		if('1' == c_choice) {
			char course_id = cli.get_input("Please enter the course id");
			Course c (course_id);
			int cv_choice = 0;
			do {
				cv_choice = cli.get_input_int("Please enter the checkpoint number (0 to exit)");
				if(0 != cv_choice) {
					c.add_node(cv_choice);
				}
			} while(cv_choice > 0);
			courses.push_back(c);	
		} else if('2' == c_choice) {
			for(unsigned int i = 0; i < courses.size(); i++) {
				courses[i].format_for_screen();
			}
		}
	} while(c_choice != 'q');
}

/**
 * Checks if all data is available to print out
 * to the file, and then writes the new file
 * to the directory specified by the user
 */
void print_files() {
	if(NULL == event ||
		competitors.size() == 0 ||
		courses.size() == 0) {
		
		cli.write_screen("One or more items are not created. Please create an Event, a Competitor and a Course");
	} else {
		string path = cli.get_input_string("Please enter the folder path to create the files");
		file_io = new FileWriter(path);
		file_io->write_event_file(event);
		file_io->write_competitor_file(competitors);
		file_io->write_courses_file(courses);
	}
}
