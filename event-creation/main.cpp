#include <iostream>
#include <vector>
#include "Event.h"
#include "List.h"
#include "Competitors.h"
#include "CLI.h"

using namespace std;

void event_menu();
void entrants_menu();
void courses_menu();

CLI cli;
Event * event;


int main(int argc, char** argv) {
	/*cout << "Hello world" << endl;
	Event e ("Hello", 1,2,3,4,5);
	cout << endl;
	Course c ('d');
	c.add_node(1);
	c.add_node(2);
	c.add_node(3);
	c.add_node(4);
	c.format_for_file();
	cout << endl;

	vector<Competitor> competitors;
	Competitor c1 ('d', "Micky Mouse");
	Competitor c2 ('d', "Mini Mouse");
	competitors.push_back(c1);
	competitors.push_back(c2);
	for(unsigned int i = 0; i < competitors.size(); i++) {
		cout << i << " " ; 
		competitors[i].format_for_file();
	}*/
	char choice = 'A';
	do {
		cli.write_main_menu();
		choice = cli.get_input();
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
		}
	} while(choice != 'q');
}

void event_menu() {
	char e_choice = 'A';
	do {
		cli.write_event_menu();
		e_choice = cli.get_input();
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

void entrants_menu() {
	char en_choice = 'A';
	do {
		cli.write_entrant_menu();
		en_choice = cli.get_input();
	} while(en_choice != 'q');
}

void courses_menu() {
	char c_choice = 'A';
	do {
		cli.write_courses_menu();
		c_choice = cli.get_input();
	} while(c_choice != 'q');
}
