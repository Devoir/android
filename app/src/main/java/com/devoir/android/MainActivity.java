package com.devoir.android;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemClickListener, AdapterView.OnLongClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final Map<Integer, String> months = new HashMap<Integer, String>() {{
        put(0, "Jan");
        put(1, "Feb");
        put(2, "Mar");
        put(3, "Apr");
        put(4, "May");
        put(5, "June");
        put(6, "July");
        put(7, "Aug");
        put(8, "Sep");
        put(9, "Oct");
        put(10, "Nov");
        put(11, "Dec");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.task_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(this, null));

        taskAdapter = new TaskAdapter(this);
        taskAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_feed) {
            //addFeed();
            return true;
        } else if (id == R.id.action_filter) {
            //filter();
            return true;
        } else if (id == R.id.action_jump_to_date) {
            showCalendarDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCalendarDialog() {
        Calendar calendar = Calendar.getInstance();
        boolean isVibrate = true;
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), isVibrate);

        datePickerDialog.setYearRange(2005, 2036);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getSupportFragmentManager(), "datepicker");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Toast.makeText(MainActivity.this, day + " " + months.get(month) + " " + year, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO respond to task click
        System.out.println("Postion clicked: " + position);
    }

    public void toggleComplete(View view) {
        //TODO update Completeness of task in DB
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
