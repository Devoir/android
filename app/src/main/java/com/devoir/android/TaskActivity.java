package com.devoir.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class TaskActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener,
        AdapterView.OnItemClickListener, AdapterView.OnLongClickListener{

    DayPagerAdapter mDayPagerAdapter;
    ViewPager mViewPager;
    GoogleApiClient mGoogleApiClient;

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
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view);

        // Create an adapter that when requested, will return a fragment representing an object in
        // the collection.
        //
        // ViewPager and its adapters use support library fragments, so we must use
        // getSupportFragmentManager.
        mDayPagerAdapter = new DayPagerAdapter(getSupportFragmentManager());

        // Set up action bar.
        //final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        //actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDayPagerAdapter);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_swipe_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.logout) {
            Intent signInIntent = new Intent(this, LogIn.class);
            startActivity(signInIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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
        Toast.makeText(TaskActivity.this, day + " " + months.get(month) + " " + year, Toast.LENGTH_LONG).show();
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

    public static class DayPagerAdapter extends FragmentStatePagerAdapter {

        public DayPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DayObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DayObjectFragment.ARG_OBJECT, i + 1); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return 10;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, position);
            SimpleDateFormat df = new SimpleDateFormat("EEE, dd MMM yyy");
            return df.format(cal.getTime());
        }
    }

    public static class DayObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";
        private RecyclerView recyclerView;
        private TaskAdapter taskAdapter;
        private RecyclerView.LayoutManager layoutManager;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)  {
            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            Bundle args = getArguments();
            recyclerView = (RecyclerView) rootView.findViewById(R.id.task_recycler_view);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new DividerDecoration(getActivity(), null));

            taskAdapter = new TaskAdapter(getActivity());
            taskAdapter.setOnItemClickListener((TaskActivity) getActivity());
            recyclerView.setAdapter(taskAdapter);

            //((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }
}
