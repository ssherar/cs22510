class CLI {
	public:
		CLI();
		static void write_main_menu();
		static void write_event_menu();
		static void write_entrant_menu();
		static void write_courses_menu();
		static char get_input(std::string);
		static std::string get_input_string(std::string);
		static int get_input_int(std::string);
		static void write_screen(std::string);
};
