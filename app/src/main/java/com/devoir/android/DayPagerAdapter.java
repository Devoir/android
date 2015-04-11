package com.devoir.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Brady on 3/13/2015.
 */
public class DayPagerAdapter extends FragmentStatePagerAdapter {
    Date currentDate;
    int currentIndex;
    List<Date> dates;
    SimpleDateFormat df;

    public DayPagerAdapter(FragmentManager fm, Date day) {
        super(fm);
        dates = new ArrayList<Date>();
        df = new SimpleDateFormat("EEE, dd MMM yyy");
        Calendar cal = Calendar.getInstance();
        currentDate = day;
        cal.setTime(day);
        dates.add(cal.getTime());
        cal.add(Calendar.DATE,-1);
        for(int i = 0; i < 30; i++) {
            dates.add(0, cal.getTime());
            cal.add(Calendar.DATE, -1);
        }
        cal.setTime(day);
        cal.add(Calendar.DATE, 1);
        for(int i = 0; i < 30; i++) {
            dates.add(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new DayObjectFragment();
        Bundle args = new Bundle();
        args.putLong(DayObjectFragment.ARG_OBJECT, dates.get(i).getTime()); // Our object is just an integer :-P
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return df.format(dates.get(position).getTime());
    }


}
