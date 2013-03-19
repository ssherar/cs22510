#include <vector>
class Course;
class CP_Node;

class Course {
	private:
		CP_Node * head;
		
	public:
		char course_id;
		Course(char);
		~Course();
		void add_node(int);
		void format_for_file();

};

class CP_Node {
	public:
		int node_id;
		CP_Node * next;
		CP_Node(int);	
};
