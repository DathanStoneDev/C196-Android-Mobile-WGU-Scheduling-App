package com.example.dathan_stone_c196_task.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;
import com.example.dathan_stone_c196_task.utilities.CourseAlertReceiver;
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
                String instructorName = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_NAME);
                String instructorPhone = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_PHONE);
                String instructorEmail = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_EMAIL);
                int startCourseAlarmId = data.getIntExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, -1);
                int endCourseAlarmId = data.getIntExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, -1);

                Course course = new Course(title, startDate, endDate, status, notes, termId, instructorName, instructorPhone, instructorEmail, startCourseAlarmId, endCourseAlarmId);
                courseViewModel.insert(course);

            } else if (code == RESULT_OK && data.hasExtra(CourseDetailsActivity.EXTRA_COURSE_ID)) {
                int courseId = data.getIntExtra(CourseDetailsActivity.EXTRA_COURSE_ID, -1);
                String title = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE);
                Date startDate = new Date (data.getLongExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, -1));
                Date endDate = new Date(data.getLongExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, -1));
                String notes = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES);
                String status = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS);
                int termId = data.getIntExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, -1);
                String instructorName = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_NAME);
                String instructorPhone = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_PHONE);
                String instructorEmail = data.getStringExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_EMAIL);
                int startCourseAlarmId = data.getIntExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, -1);
                int endCourseAlarmId = data.getIntExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, -1);

                Course course = new Course(title, startDate, endDate, status, notes, termId, instructorName, instructorPhone, instructorEmail, startCourseAlarmId, endCourseAlarmId);
                course.setCourseId(courseId);
                courseViewModel.update(course);

            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        setTitle("Courses");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final CoursesAdapter adapter = new CoursesAdapter(new CoursesAdapter.OnItemClickListener() {

            @Override
            public void deleteCourse(CourseWithAssessments delete) {

                AlarmManager alarmManager;

                System.out.println(delete.course.getStartCourseAlarmId());
                System.out.println(delete.course.getEndCourseAlarmId());

                //Cancel start Alarm
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent startAlarmIntent = new Intent(getApplicationContext(), CourseAlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), delete.course.getStartCourseAlarmId(), startAlarmIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingIntent);


                // Cancel end alarm
                Intent endAlarmIntent = new Intent(getApplicationContext(), CourseAlertReceiver.class);
                PendingIntent pendingEndIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), delete.course.getEndCourseAlarmId(), endAlarmIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingEndIntent);

                //Delete the course entirely.
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
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, details.course.getTermId());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_NAME, details.course.getInstructorName());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_PHONE, details.course.getInstructorPhoneNumber());
                intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_EMAIL, details.course.getInstructorEmail());
                intent.putExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, details.course.getStartCourseAlarmId());
                intent.putExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, details.course.getEndCourseAlarmId());
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