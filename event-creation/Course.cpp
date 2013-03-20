#include "List.h"
#include <iostream>
#include <vector>

using namespace std;

Course::Course(char id) {
	this->course_id = id;
}

Course::~Course() {

}

void Course::add_node(int node_id) {
	this->list.push_back(node_id);
}

void Course::format_for_screen() {
	cout << this->course_id << " ";
	for(unsigned int i = 0; i < list.size(); i++) {
		cout << list[i] << " ";
	}
	cout << endl;
}

