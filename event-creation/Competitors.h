#ifndef COMPETITORS_H
#define COMPETITORS_H

using namespace std;


class Competitor {
		
	public:
		char course_id;
		string name;
		Competitor(char, string);
		//~Competitor();
		void format_for_file();

};

#endif

