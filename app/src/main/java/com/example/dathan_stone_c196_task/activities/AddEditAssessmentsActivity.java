package com.example.dathan_stone_c196_task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.util.ArrayList;

public class AddEditAssessmentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ASSESSMENT_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TITLE";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_START = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_START";
    public static final String EXTRA_ASSESSMENT_END = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_END";
    public static final String EXTRA_ASSESSMENT_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_COURSE_ID";

    private EditText assessmentTitleInput;
    private EditText assessmentStart;
    private EditText assessmentEnd;
    private Spinner assessmentTypeSpinner;
    private Spinner courseSpinner;
    private ArrayList<Course> courses = new ArrayList<>();
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessments);

        assessmentTitleInput = findViewById(R.id.assessmentTitleInput);
        assessmentStart = findViewById(R.id.assessmentStartInput);
        assessmentEnd = findViewById(R.id.assessmentEndInput);
        assessmentTypeSpinner = findViewById(R.id.assessment_types_spinner);
        courseSpinner = findViewById(R.id.course_assessment_spinner);

        Intent intent = getIntent();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
        assessmentTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Course> courseTermAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_dropdown_item, courses);
        courseTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseTermAdapter);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourses().observe(this, addCourses -> {
            courses.addAll(addCourses);
            courseTermAdapter.notifyDataSetChanged();
        });

        setTitle("Add Assessment");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveAssessment:
                saveAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveAssessment() {
        String title = assessmentTitleInput.getText().toString();
        String start = assessmentStart.getText().toString();
        String end = assessmentEnd.getText().toString();
        String type = assessmentTypeSpinner.getSelectedItem().toString();
        Course course = (Course) courseSpinner.getSelectedItem();
        int courseId = course.getCourseId();

        Intent data = new Intent();
        data.putExtra(EXTRA_ASSESSMENT_TITLE, title);
        data.putExtra(EXTRA_ASSESSMENT_START, start);
        data.putExtra(EXTRA_ASSESSMENT_END, end);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);
        data.putExtra(EXTRA_ASSESSMENT_COURSE_ID, courseId);

        setResult(RESULT_OK, data);
        finish();
    }
}