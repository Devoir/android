package com.devoir.android.Database;

import android.content.Context;

import com.devoir.android.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class DBContract {

    public static final String DB_NAME = "devoir_database.db";

    public abstract class TaskTable {
        public static final String TABLE_NAME = "task_table";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String ICAL_ID = "iCalID";
        public static final String DUE_DATE = "dueDate";
        public static final String MARKED_COMPLETED = "markedCompleted";
        public static final String VISIBLE = "visible";
    }

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
            due = ft.parse("2015-04-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Task dummyTask = new Task("1", "", "HW 1", "Very difficult homework", 0xFF4C87E1,
                due, false, true);

        result.add(dummyTask);

        try {
            due = ft.parse("2015-04-11");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dummyTask = new Task("2", "", "HW 2", "Even more difficult homework", 0xFFF3BB18,
                due, false, true);
        result.add(dummyTask);

        return result;
    }
}