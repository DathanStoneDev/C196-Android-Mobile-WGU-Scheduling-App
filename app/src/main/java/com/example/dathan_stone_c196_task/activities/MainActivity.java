package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.dathan_stone_c196_task.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton termListButton;
    private ImageButton courseListButton;
    private ImageButton assessmentsListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termListButton = findViewById(R.id.termListButton);
        courseListButton = findViewById(R.id.courseListButton);
        assessmentsListButton = findViewById(R.id.assessmentsListButton);

        setTitle("School Scheduler Pro");

        Intent intent = new Intent();

        termListButton.setOnClickListener(view -> {
            intent.setClass(view.getContext(), TermsActivity.class);
            startActivity(intent);
        });

        courseListButton.setOnClickListener(view -> {
            intent.setClass(view.getContext(), CoursesActivity.class);
            startActivity(intent);
        });

        assessmentsListButton.setOnClickListener(view -> {
            intent.setClass(view.getContext(), AssessmentsActivity.class);
            startActivity(intent);
        });

    }
}