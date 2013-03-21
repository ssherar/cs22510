#include "List.h"
#include <iostream>
#include <vector>
#include <sstream>

using namespace std;

/**
 * Creates a course with a course identifier
 */
Course::Course(char id) {
	this->course_id = id;
}

/**
 * Deconstructor for the course class
 */
Course::~Course() {

}

/**
 * Adds node to the vector
 */
void Course::add_node(int node_id) {
	this->list.push_back(node_id);
}
/**
 * Outputs the course identifier with the list
 * nodes afterwards
 */
void Course::format_for_screen() {
	cout << this->course_id << " ";
	for(unsigned int i = 0; i < list.size(); i++) {
		cout << list[i] << " ";
	}
	cout << endl;
}

/**
 * Formats the course to a string with format
 * ID NumberNodes Nodes**
 * @returns formatted string
 */
string Course::format_for_file() {
	ostringstream os;
	os << this->course_id << " ";
	os << this->list.size() << " ";
	for(unsigned int i = 0; i < list.size(); i++) {
		os << this->list[i] << " ";
	}
	return os.str();
}

