package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dathan_stone_c196_task.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToActivity(View view) {

        Intent intent = new Intent();

        switch (view.getId()) {
            case  R.id.termListButton:
                intent.setClass(view.getContext(), TermsActivity.class);
                startActivity(intent);
                return;

            case R.id.courseListButton:
                intent.setClass(view.getContext(), CoursesActivity.class);
                startActivity(intent);
                return;

            case R.id.assessmentListButton:
                intent.setClass(view.getContext(), AssessmentsActivity.class);
                startActivity(intent);
                return;
            default:
        }
    }
}