package com.devoir.android;

import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.devoir.android.json.JsonCourse;
import com.devoir.android.utils.Utils;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class AddCourseActivity extends ActionBarActivity {

    private int[] mColors;
    private int mSelectedColor;
    private ImageButton mSwatchButton;
    private Button saveCourseBtn, cancelBtn;
    private EditText icalFeedInput, courseNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mColors = Utils.ColorUtils.colorChoice(this);
        mSelectedColor = mColors[0];
        mSwatchButton = (ImageButton) findViewById(R.id.color_circle);
        icalFeedInput = (EditText) findViewById(R.id.ical_feed_input);
        courseNameInput = (EditText) findViewById(R.id.coure_name_input);
        saveCourseBtn = (Button) findViewById(R.id.add_course_save);
        cancelBtn = (Button) findViewById(R.id.add_course_cancel);
        updateSwatchBackground();

        icalFeedInput.setText("https://learningsuite.byu.edu/iCalFeed/ical.php?courseID=7r8tHk3Pq9oO");

        mSwatchButton.setOnClickListener( new View.OnClickListener() {
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
                        updateSwatchBackground();
                    }
                });
                colorCalendar.show(getFragmentManager(),"cal");
            }
        });

        saveCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(courseNameInput.getText().toString()) && !"".equals(icalFeedInput.getText().toString())) {
                    JsonCourse newCourse = new JsonCourse();
                    newCourse.user_id = 1;
                    newCourse.color = String.format("#%06X", (0xFFFFFF & mSelectedColor));
                    newCourse.visible = true;
                    newCourse.ical_feed_url = icalFeedInput.getText().toString();
                    newCourse.name = courseNameInput.getText().toString();

                    new SaveCourseTask().execute(newCourse);


                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateSwatchBackground() {
        GradientDrawable swatchBackground = (GradientDrawable) getResources().getDrawable(R.drawable.course_color_circle);
        swatchBackground.setColor(mSelectedColor);
        //It would be great to change how we do this as setBackground requires API 16.
        mSwatchButton.setBackground(swatchBackground);
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

    private class SaveCourseTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            postData(params[0]);
            return null;
        }

        private void postData(Object data) {

            String courseJson = new Gson().toJson(data, JsonCourse.class);
            Log.d("Devoir", "Course Json: " + courseJson);
            HttpPost post = new HttpPost("http://192.168.1.116:3000/api/courses");
            post.setHeader("Content-type", "application/json");

            // add the JSON as a StringEntity
            try {
                post.setEntity(new StringEntity(courseJson));
                HttpResponse response = (new DefaultHttpClient()).execute(post);

                if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    finish();
                    Log.d("Devoir", "Course saved successfully");
                } else {
                    Log.d("Devoir", "Course not saved.  Status: " +  response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
                }
            } catch (Exception e) {
                Log.e("Devoir", "Exception When Creating Course: " + e.getMessage());
            }
        }
    }
}
