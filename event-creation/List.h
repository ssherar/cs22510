#include <vector>
#ifndef LIST_H
#define LIST_H

class Course;
class CP_Node;
using namespace std;
class Course {
	private:
		CP_Node * head;
		
	public:
		vector<int> list;
		char course_id;
		Course(char);
		~Course();
		void add_node(int);
		void format_for_screen();

};

class CP_Node {
	public:
		int node_id;
		CP_Node * next;
		CP_Node(int);	
};

#endif
