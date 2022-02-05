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
import com.example.dathan_stone_c196_task.adapters.AssessmentsAdapter;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AssessmentsActivity extends AppCompatActivity  {

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
            int assessmentId;

            if(code == RESULT_OK && !data.hasExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID)) {
                title = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE);
                type = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE);
                start = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START);
                end = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END);
                courseId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, -1);

                Assessment assessment = new Assessment(title, type, start, end, courseId);
                assessmentViewModel.insert(assessment);


            } else if(code == RESULT_OK && data.hasExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID)) {
                assessmentId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ID, -1);
                title = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TITLE);
                type = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_TYPE);
                start = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START);
                end = data.getStringExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END);
                courseId = data.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_COURSE_ID, -1);

                Assessment assessment = new Assessment(title, type, start, end, courseId);
                assessment.setId(assessmentId);
                assessmentViewModel.update(assessment);
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_assessments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AssessmentsAdapter adapter = new AssessmentsAdapter(new AssessmentsAdapter.OnItemClickListener() {
            @Override
            public void DeleteItem(Assessment assessment) {
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

        //Override OnClick, make a switch statment, check for view IDs then do methods.



    }

}