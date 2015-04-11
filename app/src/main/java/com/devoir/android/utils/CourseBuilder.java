package com.devoir.android.utils;

import com.devoir.android.model.Course;
import com.devoir.android.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Brady on 4/10/2015.
 */
public class CourseBuilder {

    private static Random rand;
    private static int nextTaskID = 0;
    private static int nextCourseID = 0;

    private static String[] taskNames = {
            "Reading",
            "Homework",
            "Quiz",
            "Midterm"
    };
    private static String[] descriptionText = {
            "Next chapter in the textbook",
            "End of chapter problems",
            "Current chapter's reading",
            ""
    };
    private static int currentTaskPosition = 0;

    public static void createCourse(String name, String iCalFeed, int color) {
        AppData.addCourse(new Course(nextCourseID++, name, iCalFeed, color));
    }

    public static ArrayList<Task> getTasks(Date date){
        ArrayList<Task> tasks = new ArrayList<Task>();
        // Usually this can be a field rather than a method variable
        if(rand == null) {
            rand = new Random();
        }

        for(Course course : AppData.courses) {
            //Generate 0-2 tasks
            int numTasks = rand.nextInt(2);
            for(int i = 0; i <= numTasks; i++) {
                Task task = new Task((nextTaskID++) + "", course.getId() + "", getTaskName(),
                        getTaskDescription(), course.getColor(), date, false, true);
                tasks.add(task);
            }
            resetTaskPosition();
        }

        return tasks;
    }

    private static String getTaskName() {
        return taskNames[currentTaskPosition];
    }

    private static String getTaskDescription() {
        return descriptionText[currentTaskPosition++];
    }

    public static void resetTaskPosition() {
        currentTaskPosition = 0;
    }
}
