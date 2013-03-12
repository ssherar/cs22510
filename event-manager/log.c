#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int log_file = -1;
FILE* log_file_ptr = 0x0;

struct flock* file_lock(short type, short whence) {
	static struct flock ret;
	ret.l_type = type;
	ret.l_start = 0;
	ret.l_whence = whence;
	ret.l_len = 0;
	ret.l_pid = getpid();
	return &ret;
}

void load_log_file(char filename[]) {
	log_file = open(filename, O_RDWR);
	struct flock* fl = file_lock(F_WRLCK, SEEK_SET);
	
	if(fcntl(log_file, F_SETLK, fl) != -1) {
		printf("Is now locked\n");
	}
	log_file_ptr = fopen(filename, "a");
}

void close_log_file() {
	fclose(log_file_ptr);
	if(fcntl(log_file, F_SETLKW, file_lock(F_UNLCK, SEEK_SET)) != -1) {
		printf("Is now unlocked\n");
	}
}

void append_log_file() {
	fprintf(log_file_ptr, "%s\n", "Hello World");
}
