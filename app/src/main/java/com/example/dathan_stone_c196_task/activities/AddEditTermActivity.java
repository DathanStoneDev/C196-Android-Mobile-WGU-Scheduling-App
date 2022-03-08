package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.util.ArrayList;


public class AddEditTermActivity extends AppCompatActivity {

    private EditText termTitleInput;
    private TextView termStartInput;
    private TextView termEndInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        termTitleInput = findViewById(R.id.termTitleInput);
        termStartInput = findViewById(R.id.termStartDate);
        termEndInput = findViewById(R.id.termEndDate);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        /*Intent intent = getIntent();
        if(intent.hasExtra()) {
            setTitle("Update Term");
            termTitleInput.setText(intent.getStringExtra(EXTRA_TITLE));
            termStartInput.setText(intent.getStringExtra(EXTRA_START_DATE));
            termEndInput.setText(intent.getStringExtra(EXTRA_END_DATE));

            RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            final CoursesAdapter adapter = new CoursesAdapter();
            recyclerView.setAdapter(adapter);
            courseViewModel.findTermCourses(intent.getIntExtra(EXTRA_ID, -1)).observe(
                    this, adapter::setCourseList

            );
        } else {
            setTitle("Add Term");
        } */

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

        /*Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish(); */
    }
}