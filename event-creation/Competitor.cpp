#include <iostream>
#include <vector>
#include "Competitors.h"

using namespace std;

Competitor::Competitor(char course_id, string name) {
	this->course_id = course_id;
	this->name = name;
}


void Competitor::format_for_file() {
	cout << this->course_id << " " << this->name << endl;
}
