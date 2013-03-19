#include <iostream>
#include <vector>
#include "Event.h"
#include "List.h"
#include "Competitors.h"
#include "CLI.h"

using namespace std;

CLI cli;
void blah();

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
				blah();
				break;
			case '2':
				break;
			case '3':
				break;
		}
	} while(choice != 'q');
}

void blah() {
	cout << "blah" << endl;
}
