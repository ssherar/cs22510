#include "List.h"
#include <iostream>
#include <vector>

using namespace std;

vector<CP_Node> list;

Course::Course(char id) {
	this->course_id = id;
}

Course::~Course() {

}

void Course::add_node(int node_id) {
	CP_Node tmp = CP_Node(node_id);
	list.push_back(tmp);
}

CP_Node::CP_Node(int node_id) {
	this->node_id = node_id;
}

void Course::format_for_file() {
	cout << this->course_id << " ";
	for(unsigned int i = 0; i < list.size(); i++) {
		cout << list[i].node_id << " ";
	}
	cout << endl;
}
