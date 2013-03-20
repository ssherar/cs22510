#include <vector>
#include "Event.h"
#include "List.h"
#include "Competitors.h"

using namespace std;

class FileWriter {
	private:
		string path;

	public:
		FileWriter(string);
		void write_event_file(Event*);
		void write_courses_file(vector<Course>);
		void write_competitor_file(vector<Competitor>);
};
