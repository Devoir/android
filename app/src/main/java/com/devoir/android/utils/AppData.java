package com.devoir.android.utils;

import com.devoir.android.model.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brady on 4/9/2015.
 */
public class AppData {

    public static List<Course> courses;

    public static void addCourse(Course newCourse) {
        if(courses == null) {
            courses = new ArrayList<Course>();
        }
        courses.add(newCourse);
    }
}
