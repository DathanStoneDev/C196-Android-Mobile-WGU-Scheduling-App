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
import android.os.Parcelable;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesAdapter;
import com.example.dathan_stone_c196_task.adapters.CoursesInTermAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    private CourseViewModel courseViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();


            if (code == RESULT_OK && !data.hasExtra(CourseDetailsActivity.EXTRA_COURSE_ID)) {
                String title = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE);
                Date startDate = new Date(data.getLongExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, -1));
                Date endDate = new Date(data.getLongExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, -1));
                String notes = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES);
                String status = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS);
                int termId = data.getIntExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, -1);

                Course course = new Course(title, startDate, endDate, status, notes, termId);
                courseViewModel.insert(course);
            } else if (data.hasExtra(CourseDetailsActivity.EXTRA_COURSE_ID)) {


            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final CoursesAdapter adapter = new CoursesAdapter(new CoursesAdapter.OnItemClickListener() {

            @Override
            public void deleteCourse(CourseWithAssessments delete) {
                courseViewModel.delete(delete.course);
            }

            @Override
            public void detailsForCourse(CourseWithAssessments details) {
                Intent intent = new Intent(CoursesActivity.this, CourseDetailsActivity.class);
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_ID, details.course.getCourseId());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE, details.course.getTitle());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, details.course.getStartDate());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, details.course.getEndDate());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, details.course.getStatus());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES, details.course.getNote());
                List courseAssessments = details.getAssessments();
                intent.putParcelableArrayListExtra(CourseDetailsActivity.EXTRA_ASSESSMENTS_IN_COURSE, (ArrayList<? extends Parcelable>) courseAssessments);

                resultLauncher.launch(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourseDetails().observe(this, courses -> adapter.setCourseDetailList(courses));

        FloatingActionButton addCourseButton = findViewById(R.id.addNewCourseButton);
        addCourseButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddCourseActivity.class);
            resultLauncher.launch(intent);
        });


    }
}