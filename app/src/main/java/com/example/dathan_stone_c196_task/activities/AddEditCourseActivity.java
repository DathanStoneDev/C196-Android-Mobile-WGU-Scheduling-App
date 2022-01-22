package com.example.dathan_stone_c196_task.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.util.ArrayList;

public class AddEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TERM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TITLE";
    public static final String EXTRA_COURSE_START = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_START";
    public static final String EXTRA_COURSE_END = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_END";
    public static final String EXTRA_COURSE_TYPE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TYPE";

    private EditText courseTitleInput;
    private EditText courseStart;
    private EditText courseEnd;
    private Spinner courseStatusSpinner;
    private Spinner courseTermSpinner;
    private ArrayList<Term> allTerms = new ArrayList<>();
    private TermViewModel termViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //views
        courseTitleInput = findViewById(R.id.courseTitleInput);
        courseStart = findViewById(R.id.courseStartInput);
        courseEnd = findViewById(R.id.courseEndInput);
        courseStatusSpinner = findViewById(R.id.course_type_spinner);
        courseTermSpinner = findViewById(R.id.course_term_spinner);

        //Data passed from the CourseActivity
        Intent intent = getIntent();

        //course_status spinner setup
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.course_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(adapter);
        courseStatusSpinner.setOnItemSelectedListener(this);

        //course_term spinner setup
        ArrayAdapter<Term> courseTermAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_dropdown_item, allTerms);
        courseTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseTermSpinner.setAdapter(courseTermAdapter);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTerms().observe(this, terms -> {
            allTerms.addAll(terms);
            courseTermAdapter.notifyDataSetChanged();
        });


        /*Checks for a Course ID. If a Course ID exist, this signals an update to an existing entry.
          else, it sets the title to "add course" and has blank editable text fields. */
        if(intent.hasExtra(EXTRA_COURSE_ID)) {
            setTitle("Update Course");
            courseTitleInput.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));
            courseStart.setText(intent.getStringExtra(EXTRA_COURSE_START));
            courseEnd.setText(intent.getStringExtra(EXTRA_COURSE_END));
            courseStatusSpinner.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_COURSE_TYPE)));
        } else {
            setTitle("Add Course");
        }
    }

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String selectedType = parent.getItemAtPosition(i).toString();

        Toast.makeText(this, selectedType, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveCourse:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCourse() {
        String title = courseTitleInput.getText().toString();
        String start = courseStart.getText().toString();
        String end = courseEnd.getText().toString();
        String status = courseStatusSpinner.getSelectedItem().toString();
        Term term = (Term) courseTermSpinner.getSelectedItem();
        int termId = term.getId();

        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        Intent data = new Intent();

        data.putExtra(EXTRA_COURSE_TITLE, title);
        data.putExtra(EXTRA_COURSE_START, start);
        data.putExtra(EXTRA_COURSE_END, end);
        data.putExtra(EXTRA_COURSE_TYPE, status);
        data.putExtra(EXTRA_COURSE_TERM_ID, termId);

        if(id != -1) {
            data.putExtra(EXTRA_COURSE_ID, id);
        }

        setResult(RESULT_OK, data);
        System.out.println(data.getStringExtra(EXTRA_COURSE_TYPE) + " - From the save");
        System.out.println(data.getIntExtra(EXTRA_COURSE_TERM_ID, -1) + "- From the save - course Term ID");
        finish();
    }
}
