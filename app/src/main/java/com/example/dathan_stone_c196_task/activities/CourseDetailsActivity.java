package com.example.dathan_stone_c196_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.AssessmentsInCourseAdapter;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TITLE";
    public static final String EXTRA_COURSE_START_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_END_DATE";
    public static final String EXTRA_COURSE_STATUS = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_STATUS";
    public static final String EXTRA_ASSESSMENTS_IN_COURSE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENTS_IN_COURSE";
    public static final String EXTRA_COURSE_TERM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_NOTES = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_NOTES";

    private EditText courseTitleInput;
    private TextView courseStartInput;
    private TextView courseEndInput;
    private Spinner courseStatus;
    private String courseNotes;
    private List<Assessment> assessmentsInCourse = new ArrayList<>();
    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        Intent intent = getIntent();

        courseTitleInput = findViewById(R.id.course_assessments_details_title);
        courseStartInput = findViewById(R.id.course_details_start_date);
        courseEndInput = findViewById(R.id.course_details_end_date);
        assessmentsInCourse = new ArrayList<>(intent.getParcelableArrayListExtra(EXTRA_ASSESSMENTS_IN_COURSE));

        RecyclerView recyclerView = findViewById(R.id.assessments_in_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        final AssessmentsInCourseAdapter adapter = new AssessmentsInCourseAdapter(assessment -> {
            assessment.setCourseId(0);
            assessmentViewModel.update(assessment);
        }, assessmentsInCourse);
        recyclerView.setAdapter(adapter);

        courseTitleInput.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));
        courseStartInput.setText(intent.getStringExtra(EXTRA_COURSE_START_DATE));
        courseEndInput.setText(intent.getStringExtra(EXTRA_COURSE_END_DATE));
    }
}
