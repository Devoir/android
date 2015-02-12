package com.devoir.android.Database;

import android.content.Context;
import android.graphics.Color;

import com.devoir.android.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class DBContract {

    // empty constructor in case we try to instantiate the
    // final class
    public DBContract() {}

    /**
     * This method will load all tasks out of the local database.
     * For now, it returns canned data to be displayed in the task list view.
     * @param context
     * @return
     */
    public static ArrayList<Task> loadTasks(Context context) {
        ArrayList<Task> result = new ArrayList<Task>();

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date created = new Date();
        Date start = new Date();
        Date due = new Date();

        try {
            created = ft.parse("2015-08-01");
            start = ft.parse("2015-09-01");
            due = ft.parse("2015-09-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Task dummyTask = new Task("1", "HW 1", "Very difficult homework", new Color(),
                created, start, due, false);

        result.add(dummyTask);

        try {
            start = ft.parse("2015-10-01");
            due = ft.parse("2015-10-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dummyTask = new Task("2", "HW 2", "Even more difficult homework", new Color(),
                created, start, due, false);
        result.add(dummyTask);

        return result;
    }
}