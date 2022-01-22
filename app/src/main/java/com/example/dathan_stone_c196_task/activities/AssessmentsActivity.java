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
import android.view.View;
import android.widget.ImageButton;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.AssessmentsAdapter;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssessmentsActivity extends AppCompatActivity {

    private AssessmentViewModel assessmentViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();
            String title;
            String type;
            String start;
            String end;
            int courseId;

            if(code == RESULT_OK) {
                title = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE);
                type = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE);
                start = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START);
                end = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END);
                courseId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, -1);

                Assessment assessment = new Assessment(title, type, start, end, courseId);
                assessmentViewModel.insert(assessment);


            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assessments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AssessmentsAdapter adapter = new AssessmentsAdapter();
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