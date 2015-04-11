package com.devoir.android.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brady on 3/14/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Table Types
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DATE_TYPE = " DATE";
    private static final String BOOLEAN_TYPE = " BOOLEAN";

    /**
     * CREATE TABLE
     */
    private static final String CREATE_TASK_TABLE = "CREATE TABLE "
            + DBContract.TaskTable.TABLE_NAME + " ("
            + DBContract.TaskTable.ID + TEXT_TYPE + ","
            + DBContract.TaskTable.ICAL_ID + TEXT_TYPE + ","
            + DBContract.TaskTable.NAME + TEXT_TYPE + ","
            + DBContract.TaskTable.DESCRIPTION + TEXT_TYPE + ","
            + DBContract.TaskTable.DUE_DATE + DATE_TYPE + ","
            + DBContract.TaskTable.MARKED_COMPLETED + BOOLEAN_TYPE  + " ,"
            + DBContract.TaskTable.VISIBLE + BOOLEAN_TYPE + ")";

    /**
     * DROP TABLE
     */
    private static final String DROP_TASK_TABLE = "DROP TABLE IF EXISTS " + DBContract.TaskTable.TABLE_NAME;


    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBContract.DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TASK_TABLE);
        onCreate(db);
    }
}
