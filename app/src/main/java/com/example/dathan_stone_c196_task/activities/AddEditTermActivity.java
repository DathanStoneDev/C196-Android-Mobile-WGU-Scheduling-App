package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dathan_stone_c196_task.R;


public class AddEditTermActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.example.dathan_stone_c196_task.activities.EXTRA_TITLE";

    public static final String EXTRA_START_DATE =
            "com.example.dathan_stone_c196_task.activities.EXTRA_START_DATE";

    public static final String EXTRA_END_DATE =
            "com.example.dathan_stone_c196_task.activities.EXTRA_END_DATE";

    public static final String EXTRA_ID =
            "com.example.dathan_stone_c196_task.activities.EXTRA_ID";

    private EditText termTitleInput;
    private EditText termStartInput;
    private EditText termEndInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        termTitleInput = findViewById(R.id.termTitleInput);
        termStartInput = findViewById(R.id.termStartDate);
        termEndInput = findViewById(R.id.termEndDate);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Update Term");
            termTitleInput.setText(intent.getStringExtra(EXTRA_TITLE));
            termStartInput.setText(intent.getStringExtra(EXTRA_START_DATE));
            termEndInput.setText(intent.getStringExtra(EXTRA_END_DATE));
        } else {
            setTitle("Add Term");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveTerm:
                saveNewTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNewTerm() {
        String title = termTitleInput.getText().toString();
        String start = termStartInput.getText().toString();
        String end = termEndInput.getText().toString();
        
        if(title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {
            Toast.makeText(this, "Please ensure all fields are filled out", Toast.LENGTH_SHORT).show();
            return;
        }
        //See how to insert with ViewModel
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}