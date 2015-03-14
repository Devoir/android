package com.devoir.android;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemClickListener, AdapterView.OnLongClickListener {

    DayPagerAdapter mDayPagerAdapter;
    DayObjectFragment mDayObjectFragment;
    ViewPager mViewPager;
    GoogleApiClient mGoogleApiClient;
    Date currentDate;

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

    private ActionMode mActionMode;
    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_hide, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_hide:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Indicies of tasks to hide:\n");
                    for (Integer i : mDayObjectFragment.getTaskAdapter().getSelectedItems()) {
                        sb.append(i + " ");
                    }
                    System.out.println(sb.toString().trim());
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mDayObjectFragment.getTaskAdapter().clearSelections();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_view);

        mViewPager = (ViewPager) findViewById(R.id.pager);
       /* mViewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(position < 3 || )
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            }

        );
*/
        updatePagerView(new Date());
    }

    public void toggleComplete(View view) {
        //TODO update Completeness of task in DB
    }

    public void setFragment(Fragment f) {
        this.mDayObjectFragment = (DayObjectFragment) f;
    }

    private void updatePagerView(Date day) {

        currentDate = day;
        mDayPagerAdapter = new DayPagerAdapter(getSupportFragmentManager(), currentDate);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDayPagerAdapter);
        mViewPager.setCurrentItem(30);
    }

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
        calendar.setTime(currentDate);
        boolean isVibrate = true;
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), isVibrate);

        datePickerDialog.setYearRange(2010, 2020);
        datePickerDialog.setCloseOnSingleTapDay(true);
        datePickerDialog.show(getSupportFragmentManager(), "datepicker");
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Toast.makeText(TaskActivity.this, day + " " + months.get(month) + " " + year, Toast.LENGTH_LONG).show();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        updatePagerView(cal.getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO respond to a regular task click
        System.out.println("Postion clicked: " + position);
        if (mActionMode != null) {
            mDayObjectFragment.getTaskAdapter().toggleSelection(position);
            return;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        System.out.println("loooong click");
        if (mActionMode != null) {
            return false;
        }
        // Start the CAB using the ActionMode.Callback defined above
        mActionMode = this.startActionMode(mActionModeCallback);
        mDayObjectFragment.getTaskAdapter().toggleSelection(mDayObjectFragment.getRecyclerView().getChildPosition(v));
        return true;
    }
}
