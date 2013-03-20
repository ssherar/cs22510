#include <fstream>
#include <vector>
#include <iostream>
#include "FileWriter.h"
#include "Event.h"

using namespace std;

FileWriter::FileWriter(string path) {
	this->path = path;
}

void FileWriter::write_event_file(Event * event) {
	string file_path = this->path.c_str();
	file_path.append("event.txt");
	cout << file_path << endl;
	ofstream file (file_path.c_str());
	if(file.is_open()) {
		file << event->get_name() << "\n";
		file << event->get_date() << "\n";
	}
	file.close();
}

void FileWriter::write_competitor_file(vector<Competitor> competitors) {
	string file_path = this->path.c_str();
	file_path.append("comp_data.txt");
	ofstream file(file_path.c_str());
	if(file.is_open()) {
		for(unsigned int i = 0; i < competitors.size(); i++) {
			file << (i + 1) << " ";
			file << competitors[i].course_id << " ";
			file << competitors[i].name << "\n";
		}
	}


}


