#include <iostream>
#include "CLI.h"

using namespace std;

CLI::CLI() {}

void CLI::write_main_menu() {
	cout << endl;
	cout << " Welcome to Event Creation " << endl;
	cout << endl;
	cout << " 1) Create Event Info" << endl;
	cout << " 2) Add Entrants " << endl;
	cout << " 3) Add Courses " << endl;
	cout << " q) Quit" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

void CLI::write_event_menu() {
	cout << endl;
	cout << " Event: " << endl;
	cout << " 1) Add new title" << endl;
	cout << " 2) Add new time" << endl;
	cout << " q) Quit back to main menu" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

void CLI::write_entrant_menu() {
	cout << endl;
	cout << " Entrants:" << endl;
	cout << " 1) Add Entrant" << endl;
	cout << " 2) List Entrants" << endl;
	cout << " q) Quit back to main menu" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

char CLI::get_input() {
	char input;
	cin >> input;
	return input;
}
