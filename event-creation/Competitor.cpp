#include <iostream>
#include <vector>
#include "Competitors.h"

using namespace std;

/**
 * Constructor for the competitor, which sets the name
 * and course identifier
 * @param course_id the course identifier
 * @param name the name of the competitor
 */
Competitor::Competitor(char course_id, string name) {
	this->course_id = course_id;
	this->name = name;
}

