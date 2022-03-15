package com.example.dathan_stone_c196_task.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText courseTitleInput;
    private TextView courseStart;
    private TextView courseEnd;
    private Spinner courseStatusSpinner;
    private Spinner courseTermSpinner;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private EditText courseNotes;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    Calendar startDateCalendar;
    Calendar endDateCalendar;
    private TermViewModel termViewModel;
    private ArrayList<Term> allTerms = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        //views
        courseTitleInput = findViewById(R.id.course_title_input);
        courseStart = findViewById(R.id.course_start_date);
        courseEnd = findViewById(R.id.course_end_date);
        startDateButton = findViewById(R.id.course_start_date_picker);
        endDateButton = findViewById(R.id.course_end_date_picker);
        courseStatusSpinner = findViewById(R.id.course_type_spinner);
        courseTermSpinner = findViewById(R.id.course_terms_spinner);
        courseNotes = findViewById(R.id.course_notes);

        //Data passed from the CourseActivity
        Intent intent = getIntent();


        setTitle("Add New Course");

        startDateButton.setOnClickListener(view -> {
            startDateCalendar = Calendar.getInstance();
            int mYear = startDateCalendar.get(Calendar.YEAR);
            int mMonth = startDateCalendar.get(Calendar.MONTH);
            int mDay = startDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddCourseActivity.this, (datePicker, year, month, day) -> {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, month);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                courseStart.setText(sdf.format(startDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        endDateButton.setOnClickListener(view -> {
            endDateCalendar = Calendar.getInstance();
            int mYear = endDateCalendar.get(Calendar.YEAR);
            int mMonth = endDateCalendar.get(Calendar.MONTH);
            int mDay = endDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddCourseActivity.this, (datePicker, year, month, day) -> {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, month);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                courseEnd.setText(sdf.format(endDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

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
                try {
                    saveCourse();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveCourse() throws ParseException {
        String title = courseTitleInput.getText().toString();
        String startDateToParse = courseStart.getText().toString();
        String endDateToParse = courseEnd.getText().toString();
        Date start = sdf.parse(startDateToParse);
        Date end = sdf.parse(endDateToParse);
        String status = courseStatusSpinner.getSelectedItem().toString();
        String note = courseNotes.getText().toString();
        Term term = (Term) courseTermSpinner.getSelectedItem();
        int termId = term.getId();

        Intent data = new Intent();

        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE, title);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, start);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, end);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, status);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES, note);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, termId);

        setResult(RESULT_OK, data);
        finish();
    }
}
