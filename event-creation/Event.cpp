#include <iostream>
#include "Event.h"

Event::Event(std::string info, int day, int month, int year, int hour, int minute) {
	std::cout << "In constructor for Event" << std::endl;
	std::cout << "Info: " << info << std::endl;
	std::printf("On the %02d/%02d/%04d \n", day, month, year);
	std::printf("At %02d:%02d \n", hour, minute);	
}
