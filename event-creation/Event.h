#include <string>
class Event {
	std::string event_name;
	int day, month, year, hour, minute;
	public:
		Event(std::string, int, int, int, int, int);
	//	~Event();
		char* format_printing_file();
};
