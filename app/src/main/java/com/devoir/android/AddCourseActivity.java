package com.devoir.android;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.devoir.android.utils.Utils;


public class AddCourseActivity extends ActionBarActivity {

    private int[] mColors;
    private int mSelectedColor;
    private ImageButton mSwatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mColors = Utils.ColorUtils.colorChoice(this);
        mSwatchButton = (ImageButton) findViewById(R.id.color_circle);
        mSelectedColor = mColors[0];

        ImageButton addCourseBtn = (ImageButton) findViewById(R.id.color_circle);
        addCourseBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorCalendar = ColorPickerDialog.newInstance(
                        R.string.color_picker_default_title,
                        mColors, mSelectedColor, 4,
                        ColorPickerDialog.SIZE_SMALL);
                colorCalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int i) {
                        mSelectedColor = i;
                        GradientDrawable swatchBackground = (GradientDrawable) getResources().getDrawable(R.drawable.course_color_circle);
                        swatchBackground.setColor(mSelectedColor);
                        //It would be great to change how we do this as setBackground requires API 16.
                        mSwatchButton.setBackground(swatchBackground);

                    }
                });
                colorCalendar.show(getFragmentManager(),"cal");
            }
        });

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }*/
}
