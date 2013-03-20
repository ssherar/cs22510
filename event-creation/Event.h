#include <string>

#ifndef EVENT_H
#define EVENT_H
class Event {
	std::string event_name;
	int day, month, year, hour, minute;
	public:
		Event(std::string, int, int, int, int, int);
		//Event();
		char* format_printing_file();
		std::string format_for_screen();
		std::string get_name();
		std::string get_date();
};
#endif
