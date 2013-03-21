#include <iostream>
#include "CLI.h"

using namespace std;

CLI::CLI() {}

/**
 * Writes out the main menu to screen
 */
void CLI::write_main_menu() {
	cout << endl;
	cout << " Welcome to Event Creation " << endl;
	cout << endl;
	cout << " 1) Create Event Info" << endl;
	cout << " 2) Add Entrants " << endl;
	cout << " 3) Add Courses " << endl;
	cout << " 4) Print to file" << endl;
	cout << " q) Quit" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

/**
 * Writes out the event menu to screen
 */
void CLI::write_event_menu() {
	cout << endl;
	cout << " Event: " << endl;
	cout << " 1) Add new Event" << endl;
	cout << " 2) Print out current event" << endl;
	cout << " q) Quit back to main menu" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

/**
 * Writes out the entrants menu to the screen
 */
void CLI::write_entrant_menu() {
	cout << endl;
	cout << " Entrants:" << endl;
	cout << " 1) Add Entrant" << endl;
	cout << " 2) List Entrants" << endl;
	cout << " q) Quit back to main menu" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

/**
 * Writes out the courses menu to the screen
 */
void CLI::write_courses_menu() {
	cout << endl;
	cout << " Courses:" << endl;
	cout << " 1) Add new Course " << endl;
	cout << " 2) List Courses " << endl;
	cout << " q) Quit back to main menu" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

/**
 * Writes out the value given with a newline character appended
 * @param val the message
 */
void CLI::write_screen(string val) {
	cout << val << endl;
}

/**
 * Gets a character from the input, with an overloaded question
 * if the question has already been written out to the screen
 * (such if being used with any CLI::write_X_menu()
 * @param question the optional question to be written
 * @returns the chosen character
 */
char CLI::get_input(string question) {
	if(!question.empty()) {
		cout << question << " > ";
	}
	char input;
	cin >> input;
	return input;
}

/**
 * Gets input string from the user
 * @param question the question to be asked
 * @returns string from the user
 */
string CLI::get_input_string(string question) {
	cout << question << " > ";
	string input;
	cin.ignore();
	getline(cin, input);
	return input;
}

/**
 * Gets a integer input from the user
 * @param question the question to be asked
 * @returns int from the user
 */
int CLI::get_input_int(string question) {
	cout << question << " > ";
	int input;
	cin >> input;
	return input;
}
