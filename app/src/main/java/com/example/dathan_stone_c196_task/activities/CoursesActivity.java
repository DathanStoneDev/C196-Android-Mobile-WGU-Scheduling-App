package com.example.dathan_stone_c196_task.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CoursesActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();
            String title;
            String start;
            String end;
            String status;


            if(code == RESULT_OK) {
                title = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE);
                start = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_START);
                end = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_END);
                status = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TYPE);

                Course course = new Course(title, start, end, status);
                courseViewModel.insert(course);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final CoursesAdapter adapter = new CoursesAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourses().observe(this, courses -> adapter.setCourseList(courses));

        FloatingActionButton addCourseButton = findViewById(R.id.addNewCourseButton);
        addCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddEditCourseActivity.class);
            resultLauncher.launch(intent);
        });

        adapter.setOnItemClickListener(course -> {
            Intent intent = new Intent(CoursesActivity.this, AddEditCourseActivity.class);
            intent.putExtra(AddEditTermActivity.EXTRA_TITLE, course.getTitle());
            intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, course.getStartDate());
            intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, course.getEndDate());

            resultLauncher.launch(intent);


        });
    }
}