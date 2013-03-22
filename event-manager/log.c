#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>

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
	
	if(fcntl(log_file, F_SETLK, fl) == -1) {
		printf("Unknown Error\n");
	}
	log_file_ptr = fopen(filename, "a+");
}

void close_log_file() {
	fclose(log_file_ptr);
	if(fcntl(log_file, F_SETLKW, file_lock(F_UNLCK, SEEK_SET)) == -1) {
		printf("Unknown Error!\n");
	}
}

void append_log_file(char action[]) {
	time_t dt = time(NULL);
	char buf[255];
	strftime(buf, sizeof buf, "%c", localtime(&dt));
	fprintf(log_file_ptr, "Event-Manager: [%s] %s\n", buf, action);
}

int get_lock(char filename[]) {
	struct flock* fl = file_lock(F_WRLCK, SEEK_SET);
	int fd = open(filename, O_RDWR);
	int ret  = 0;
	ret = fcntl(fd, F_GETLK, fl);
	close(fd);
	return ret;
}
