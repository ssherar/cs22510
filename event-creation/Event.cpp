#include <iostream>
#include "Event.h"
#include <string>
#include <stdio.h>

using namespace std;

/**
 * Creates an event
 * @param info  information of the event
 * @param day the day
 * @param month the month
 * @param year the year
 * @param hour the hour
 * @param minute the minute
 */
Event::Event(string info, int day, int month, int year, int hour, int minute) {
	this->event_name = info;
	this->day = day;
	this->month = month;
	this->year = year;
	this->hour = hour;
	this->minute = minute;
}
/**
 * Creates a formatted string to be outputted to
 * the screen
 * @returns the formatted string
 */
string Event::format_for_screen() {
	string ret;
	if(this->event_name.empty() ||
		0 == this->day ||
		0 == this->month ||
		0 == this->year ||
		0 == this->hour ||
		0 == this->minute) {
			ret = "No event has been created\n";
	} else {
		ret.append("Event: ");
		ret.append(this->event_name);
		ret.append("\n");
		char formatted_time[100];
		sprintf(formatted_time, "on %02d/%02d/%04d at %02d§:%02d",
					this->day,
					this->month,
					this->year,
					this->hour, 
					this->minute);
		ret.append(formatted_time);
		ret.append("\n");
	}
	return ret;
				
}

/**
 * Returns the name of the event
 * @return the name of the event
 */
string Event::get_name() {
	return this->event_name;
}

/**
 * Returns the formatted date
 * @return formatted string with date/time
 */
string Event::get_date() {
	char formatted_time[100];
	sprintf(formatted_time, "on %02d/%02d/%04d at %02d:%02d",
				this->day,
				this->month,
				this->year,
				this->hour, 
				this->minute);
	return formatted_time;
}
