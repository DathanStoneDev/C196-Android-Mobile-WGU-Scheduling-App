package com.example.dathan_stone_c196_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dathan_stone_c196_task.R;

public class AddEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TITLE";
    public static final String EXTRA_COURSE_START = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_START";
    public static final String EXTRA_COURSE_END = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_END";
    public static final String EXTRA_COURSE_TYPE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TYPE";

    private EditText courseTitleInput;
    private EditText courseStart;
    private EditText courseEnd;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //views
        courseTitleInput = findViewById(R.id.courseTitleInput);
        courseStart = findViewById(R.id.courseStartInput);
        courseEnd = findViewById(R.id.courseEndInput);
        spinner = findViewById(R.id.course_type_spinner);

        //course status spinner setup
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.course_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);





    }

    /**
     * Gets the selected course status from the spinner object.
     * @param parent
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String selectedType = parent.getItemAtPosition(i).toString();
        Toast.makeText(this, selectedType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveCourse:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCourse() {
        String title = courseTitleInput.getText().toString();
        String start = courseStart.getText().toString();
        String end = courseEnd.getText().toString();
        String status = spinner.getSelectedItem().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_TITLE, title);
        data.putExtra(EXTRA_COURSE_START, start);
        data.putExtra(EXTRA_COURSE_END, end);
        data.putExtra(EXTRA_COURSE_TYPE, status);

        setResult(RESULT_OK, data);
        finish();
    }
}
