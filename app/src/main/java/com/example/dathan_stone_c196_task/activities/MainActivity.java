package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.activities.AssessmentsActivity;
import com.example.dathan_stone_c196_task.activities.CoursesActivity;
import com.example.dathan_stone_c196_task.activities.TermsActivity;


public class MainActivity extends AppCompatActivity {

    private Button termListButton;
    private Button courseListButton;
    private Button assessmentListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons that when clicked, go to the other 3 activities
        termListButton = findViewById(R.id.termListButton);
        courseListButton = findViewById(R.id.courseListButton);
        assessmentListButton = findViewById(R.id.assessmentListButton);

        //Sets the click listener and opens the Terms Activity.
        termListButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), TermsActivity.class);
            startActivity(intent);
        });

        //Sets the click listener and opens the Courses Activity.
        courseListButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), CoursesActivity.class);
            startActivity(intent);
        });

        //Sets the click listener and opens the Assessments Activity.
        assessmentListButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AssessmentsActivity.class);
            startActivity(intent);
        });



    }
}