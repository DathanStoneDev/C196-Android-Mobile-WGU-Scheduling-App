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

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.AssessmentsAdapter;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.utilities.AssessmentAlertReceiver;
import com.example.dathan_stone_c196_task.utilities.CourseAlertReceiver;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AssessmentsActivity extends AppCompatActivity  {

    private AssessmentViewModel assessmentViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();
            String title;
            String type;
            Date startDate;
            Date endDate;
            int courseId;
            int assessmentId;
            int startAlarmId;
            int endAlarmId;

            if(code == RESULT_OK && !data.hasExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID)) {
                title = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE);
                type = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE);
                startDate = new Date(data.getLongExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START, -1));
                endDate = new Date(data.getLongExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END, -1));
                courseId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, -1);
                startAlarmId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START_ALARM_ID, -1);
                endAlarmId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END_ALARM_ID, -1);


                Assessment assessment = new Assessment(title, type, startDate, endDate, courseId,startAlarmId, endAlarmId);
                assessmentViewModel.insert(assessment);


            } else if(code == RESULT_OK && data.hasExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID)) {
                assessmentId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID, -1);
                title = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE);
                type = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE);
                startDate = new Date(data.getLongExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START, -1));
                endDate = new Date(data.getLongExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END, -1));
                courseId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, -1);
                startAlarmId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START_ALARM_ID, -1);
                endAlarmId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END_ALARM_ID, -1);

                Assessment assessment = new Assessment(title, type, startDate, endDate, courseId, startAlarmId, endAlarmId);
                assessment.setId(assessmentId);
                assessmentViewModel.update(assessment);
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        setTitle("Assessments");

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assessments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AssessmentsAdapter adapter = new AssessmentsAdapter(new AssessmentsAdapter.OnItemClickListener() {
            @Override
            public void DeleteItem(Assessment assessment) {

                AlarmManager alarmManager;

                //Cancel start Alarm
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent startAlarmIntent = new Intent(getApplicationContext(), AssessmentAlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), assessment.getStartAlarmId(), startAlarmIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingIntent);


                // Cancel end alarm
                Intent endAlarmIntent = new Intent(getApplicationContext(), AssessmentAlertReceiver.class);
                PendingIntent pendingEndIntent = PendingIntent.getBroadcast(
                        getApplicationContext(), assessment.getEndAlarmId(), endAlarmIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.cancel(pendingEndIntent);

                assessmentViewModel.delete(assessment);
            }

            @Override
            public void DetailsForItem(Assessment assessment) {


               Intent intent = new Intent(AssessmentsActivity.this, AddEditAssessmentsActivity.class);
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID, assessment.getId());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE, assessment.getAssessmentTitle());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE, assessment.getType());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START, assessment.getStartDate());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END, assessment.getEndDate());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, assessment.getCourseId());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START_ALARM_ID, assessment.getStartAlarmId());
               intent.putExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END_ALARM_ID, assessment.getEndAlarmId());

               resultLauncher.launch(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessments().observe(this, assessments -> adapter.setAssessments(assessments));

        FloatingActionButton addAssessmentButton = findViewById(R.id.addNewAssessmentButton);
        addAssessmentButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddEditAssessmentsActivity.class);
            resultLauncher.launch(intent);
        });



    }

}