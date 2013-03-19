#include <iostream>
#include "CLI.h"

using namespace std;

CLI::CLI() {}

void CLI::write_main_menu() {
	cout << " Welcome to Event Creation " << endl;
	cout << endl;
	cout << " 1) Create Event Info" << endl;
	cout << " 2) Add Entrants " << endl;
	cout << " 3) Add Courses " << endl;
	cout << " q) Quit" << endl;
	cout << endl;
	cout << "Please enter your choice > ";
}

char CLI::get_input() {
	char input;
	cin >> input;
	return input;
}
