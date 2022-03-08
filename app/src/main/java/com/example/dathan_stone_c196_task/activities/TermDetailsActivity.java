package com.example.dathan_stone_c196_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesInTermAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TERM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_TITLE";
    public static final String EXTRA_TERM_START_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_START_DATE";
    public static final String EXTRA_TERM_END_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_END_DATE";
    public static final String EXTRA_COURSES_IN_TERM = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSES_IN_TERM";

    private EditText termTitleInput;
    private TextView termStartInput;
    private TextView termEndInput;
    private List<Course> coursesInTerm = new ArrayList<>();
    private CourseViewModel courseViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Intent intent = getIntent();

        termTitleInput = findViewById(R.id.term_course_details_title);
        termStartInput = findViewById(R.id.term_details_start_date);
        termEndInput = findViewById(R.id.term_details_end_date);
        coursesInTerm = new ArrayList<>(intent.getParcelableArrayListExtra(EXTRA_COURSES_IN_TERM));

        RecyclerView recyclerView = findViewById(R.id.courses_in_terms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        final CoursesInTermAdapter adapter = new CoursesInTermAdapter(course -> {
            course.setTermId(0);
            courseViewModel.update(course);
        }, coursesInTerm);
        recyclerView.setAdapter(adapter);



        termTitleInput.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
        termStartInput.setText(intent.getStringExtra(EXTRA_TERM_START_DATE));
        termEndInput.setText(intent.getStringExtra(EXTRA_TERM_END_DATE));

        System.out.println(coursesInTerm.size());
    }
}