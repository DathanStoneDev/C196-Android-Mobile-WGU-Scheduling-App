package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Term;

public class AddTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        final EditText termTitleInput = findViewById(R.id.termTitleInput);
        final EditText termStartInput = findViewById(R.id.termEndDate);
        final EditText termEndInput = findViewById(R.id.termStartDate);
        Button saveButton = findViewById(R.id.saveTerm);
        saveButton.setOnClickListener(view -> saveNewTerm(termTitleInput.getText().toString(), termStartInput.getText().toString(), termEndInput.getText().toString()));
    }

    private void saveNewTerm(String termTitle, String termStart, String termEnd) {
        Term term = new Term(termTitle, termStart, termEnd);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplicationContext());
        appDatabase.termDAO().saveTerm(term);
        finish();


    }
}