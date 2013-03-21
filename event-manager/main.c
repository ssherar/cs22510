/*
 * =====================================================================================
 *
 *       Filename:  main.c
 *
 *    Description:  Main file for the assignment
 *
 *        Version:  1.0
 *        Created:  11/28/2012 06:33:13 PM
 *       Revision:  none
 *       Compiler:  gcc -Wall
 *
 *         Author:  Samuel Sherar (sbs1), sbs1@aber.ac.uk
 *        Company:  Aberystwyth University
 *
 * =====================================================================================
 */

#include	<stdio.h>
#include	<stdlib.h>
#include        <string.h>
#include        "fileio.h"
#include        "node.h"
#include	"log.h"
/**
 * The struct to hold the event data
 */
struct Event_Info event;

/**
 * The Competitor struct, which holds the course data for the
 * competitor
 */
struct Competitor* competitor;

/** 
 * An array which holds the ID and the type of the node (e.g. JP, CP etc)
 */
struct Node *node_types;

/**
 * An array of the different tracks between nodes
 */
Track *tracks;

/**
 * The length of the competitor and courses files, so it is
 * easy to iterate over the arrays.
 */
int no_competitors = 0, no_courses, no_tracks;

/** 
 * Method Signatures for the main.c file.
 */

void menu();
void startup();
void print_competitor(struct Competitor comp);
void print_competitors();
int find_finished(Competitor comp[], int no_comp);
int find_running(Competitor comp[], int no_comp);
int find_not_started(Competitor comp[], int no_comp);

/** 
 * Main entry point for the program, which runs setup
 * and menu.
 * @param argc - the amount of records in argv
 * @param argv - the arguments parsed from the command line
 * @return EXIT_SUCCESS
 */
int main(int argc, char *argv[]) {
    startup();
    menu();
    return EXIT_SUCCESS;
}

/**
 * The main loop for the program
 */

void menu() {
    char menu_choice = 'A';
    do {
        printf("\n\nWelcome to %s on %s %s! \n\n",
                event.name, event.date, event.time);
        printf("\t1)\tQuery Location of Competitor\n");
        printf("\t2)\tQuery status of competitors\n");
        printf("\t3)\tSupply times for individual competitor\n");
        printf("\t4)\tRead checkpoint data from file\n");
        printf("\t5)\tList competitors with times\n");
        printf("\tq)\tExit the application\n");
        printf("\n > ");
        scanf(" %c", &menu_choice);
        printf("\n\n");
        
        if ('1' == menu_choice) {
            int comp_id;
            printf("Please enter a competitor id > ");
            scanf(" %d", &comp_id);
	    append_log_file("Querying Location");
            comp_id = find_comp_index(competitor, no_competitors, comp_id);
            if(*competitor[comp_id].course.start_time == NULL) {
                printf("Competitor %s has not started yet\n", 
                        competitor[comp_id].name);
            }else if(*competitor[comp_id].course.start_time != NULL &&
                    *competitor[comp_id].course.end_time != NULL) {
                printf("Competitor %s has finished with the time of %s\n",
                        competitor[comp_id].name,
                        competitor[comp_id].course.end_time);
            } else {
                Course_Node *current_node = 
                        (Course_Node*) find_current_node(
                                competitor[comp_id].course.head);
                
                printf("Competitor %s is currently past "
                        "checkpoint %d at time %s\n",
                        competitor[comp_id].name,
                        current_node->node_id,
                        current_node->time);
                Track current_track = find_current_track(
                                competitor[comp_id].course.head,
                                tracks, no_tracks);
                printf("The competitor is estimated to be between "
                        "nodes %d and %d\n",
                        current_track.start, current_track.finish);
            }
        } else if('2' == menu_choice) {
          printf("\t1)\tQuery how many which haven't started\n");
          printf("\t2)\tQuery how many people are on the courses\n");
          printf("\t3)\tQuery how many people have finished\n");
          printf("\t4)\tQuery how many have been disqualified for "
                  "wrong checkpoint\n");
          printf("\t5)\tQuery how many have been disqualified for "
                  "medical reasons\n");
          printf("\n > ");
          scanf(" %c", &menu_choice);
	  append_log_file("Querying Status of Competitors");
          if('1' == menu_choice) {
              int not_started = find_not_started(competitor, no_competitors);
              printf("Number of competitors not started: %d\n", not_started);
          } else if ('2' == menu_choice) {
              int running = find_running(competitor, no_competitors);
              printf("Number of competitors running: %d\n", running);
          } else if('3' == menu_choice) {
              int finished = find_finished(competitor, no_competitors);
              printf("Number of competitors finished: %d\n", finished);
          } else if('4' == menu_choice) {
              int disq_cp = find_disq_cp(competitor, no_competitors);
              printf("Number of competitors disqualified for wrong cp: %d",
                      disq_cp);
          } else if('5' == menu_choice) {
              int disq_medical = find_disq_medical(competitor, no_competitors);
              printf("Number of competitors disqualified for "
                      "medical reasons : %d",
                      disq_medical);
          }
        } else if('3' == menu_choice) {
            int id = -1, node_id = -1;
            char time[6];
            printf("Please enter the ID for the competitor > ");
            scanf(" %d", &id);
            printf("Please enter the node which he has just hit > ");
            scanf(" %d", &node_id);
            printf("Please enter the time he reached the checkpoint > ");
            scanf(" %5s", time);
            insert_checkpoint_data_manually(competitor[id-1].course.head, 
                    id, node_id, time);
	    append_log_file("Manually adding time");
        } else if('4' == menu_choice) {
            char cp_data_filename[30];
            printf("Please enter the data file for the checkpoints > ");
            scanf(" %30s", cp_data_filename);
	    if(get_lock(cp_data_filename) != -1) {
		    int cp_data_lines = get_number_lines(cp_data_filename);
		    load_time_file(cp_data_filename, cp_data_lines, competitor);
		    append_log_file("Loading time file");
	    } else {
		    printf("I'm afraid it's locked by another process. Please try again later!");
	    }
        } else if('5' == menu_choice) {
            print_competitors();
        }
    } while (menu_choice != 'q');
    close_log_file();
}

/**
 * A method to find the amount of competitors which
 * haven't started, by checking the the start_time is
 * equal to NULL.
 * 
 * @param comp - the array of competitors
 * @param no_comp - the count of array
 * @return the amount of competitors which haven't started
 */
int find_not_started(Competitor comp[], int no_comp) {
    int amount = 0, i = 0;
    for(i = 0; i < no_comp; i++) {
        if(*comp[i].course.start_time == NULL) {
            amount++;
        }
    }
    return amount;
}

/**
 * A method to find the amount of competitors which
 * are in the process of competing the course by checking
 * if they have a start_time but not an end_time
 * @param comp - the array of competitors
 * @param no_comp - the count of array
 * @return the amount of competitors which are running
 */
int find_running(Competitor comp[], int no_comp) {
    int amount = 0, i = 0;
    for(i = 0; i < no_comp; i++) {
        if(*comp[i].course.start_time != NULL &&
                *comp[i].course.end_time == NULL) {
            amount++;
        }
    }
    return amount;
}

/**
 * A method to find the amount of competitors which have finished
 * the course by checking if they have both a start_time
 * and an end_time
 * @param comp - the array of competitors
 * @param no_comp - the count of array
 * @return the amount of competitors which are running
 */
int find_finished(Competitor comp[], int no_comp) {
    int amount =0, i = 0;
    for(i = 0; i < no_comp; i++) {
        if(*comp[i].course.end_time != NULL &&
                *comp[i].course.start_time != NULL) {
            amount++;
        } 
    }
    return amount;
}

/**
 * A function to find the amount of competitors which have the flag
 * WRONG_CHECKPOINT_DISQ set.
 * @param comp the array of competitors
 * @param no_comp the size of the array
 * @return  the amount of competitors disqualified
 */
int find_disq_cp(Competitor comp[], int no_comp) {
    int amount = 0, i = 0;
    for(i; i < no_comp; i++) {
        if(comp[i].flag == WRONG_CHECKPOINT_DISQ) {
            amount++;
        }
    }
    return amount;
}

/**
 * A function to find the amount of competitors which have the flag
 * MEDICAL_DISQ set.
 * @param comp the array of competitors
 * @param no_comp the size of the array
 * @return the amount of competitors disqualified
 */
int find_disq_medical(Competitor comp[], int no_comp) {
    int amount = 0, i = 0;
    for(i; i < no_comp; i++) {
        if(comp[i].flag == MEDICAL_DISQ) {
            amount++;
        }
    }
    return amount;
}

/**
 * The start up method, which takes asks the user for
 * input for all 5 files, while loading them into the
 * relevant structures.
 */
void startup() {
    char info_filename[30], competitor_filename[30], 
	 node_filename[30], course_filename[30], 
	 tracks_filename[30], log_filename[30];
    int competitor_lines, node_lines, courses_lines, tracks_lines;

    printf("Please enter the file for the logging > ");
    scanf(" %30s", log_filename);
    load_log_file(log_filename);

    printf("Please enter the file for the event information > ");
    scanf(" %30s", info_filename);
    load_info_file(info_filename, &event);

    printf("Please enter the file for the node type > ");
    scanf(" %30s", node_filename);
    node_lines = get_number_lines(node_filename);
    if (0 < node_lines) {
        node_types = (Node*) malloc(node_lines * sizeof (struct Node));
        load_node_file(node_filename, node_lines, node_types);
    }

    printf("Please enter the file for the competitors > ");
    scanf(" %30s", competitor_filename);
    competitor_lines = get_number_lines(competitor_filename);
    no_competitors = competitor_lines;
    
    if (0 < competitor_lines) {
        competitor = (Competitor*) malloc(competitor_lines * 
                sizeof (Competitor));
        load_comp_file(competitor_filename, competitor_lines, competitor);
    }
    printf("Please enter the file for the courses > ");
    scanf(" %30s", course_filename);
    courses_lines = get_number_lines(course_filename);

    if (0 < courses_lines) {
        load_courses_file(course_filename, courses_lines,
                 node_types, competitor, no_competitors);
    }

    printf("Please enter the file for the tracks > ");
    scanf(" %30s", tracks_filename);
    tracks_lines = get_number_lines(tracks_filename);

    if (0 < tracks_lines) {
        tracks = (Track*) malloc(tracks_lines * sizeof (Track));
        load_track_file(tracks_filename, tracks_lines, tracks);
    }

    no_courses = courses_lines;
    no_tracks = tracks_lines;
}

/**
 * The main function for printing out the competitors, for
 * printing out individual competitors, look at print_competitor(comp)
 */
void print_competitors() {
    int i;
    printf("%-30s %-10s %-15s %-15s %-15s %-15s\n",
            "Name",
            "ID",
            "Course ID",
            "Start Time",
            "End Time",
            "Total Time");
    for(i = 0; i < no_competitors; i++) {
        print_competitor(competitor[i]);
    }
}

/**
 * Prints out a competitor
 * @param comp the competitor
 */
void print_competitor(struct Competitor comp) {
    printf("%-30s %-10d %-15c %-15s %-15s %-15s\n",
            comp.name,
            comp.id,
            comp.course_id,
            comp.course.start_time,
            comp.course.end_time,
            calc_total_time(comp.course));
}

