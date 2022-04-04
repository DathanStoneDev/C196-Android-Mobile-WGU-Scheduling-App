package com.example.dathan_stone_c196_task.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.CoursesInTermAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TERM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_ID";
    public static final String EXTRA_TERM_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_TITLE";
    public static final String EXTRA_TERM_START_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_START_DATE";
    public static final String EXTRA_TERM_END_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_TERM_END_DATE";
    public static final String EXTRA_COURSES_IN_TERM = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSES_IN_TERM";

    private EditText termTitleInput;
    private TextView termStartInput;
    private TextView termEndInput;
    private Button editTermButton;
    private List<Course> coursesInTerm = new ArrayList<>();
    private CourseViewModel courseViewModel;
    private Calendar termStartCalendar;
    private Calendar termEndCalendar;
    private ImageButton termStartPicker;
    private ImageButton termEndPicker;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Intent intent = getIntent();

        termTitleInput = findViewById(R.id.term_courses_details_title);
        termStartInput = findViewById(R.id.term_details_start_date);
        termEndInput = findViewById(R.id.term_details_end_date);
        coursesInTerm = new ArrayList<>(intent.getParcelableArrayListExtra(EXTRA_COURSES_IN_TERM));
        termStartPicker = findViewById(R.id.edit_term_start_date_picker);
        termEndPicker = findViewById(R.id.edit_term_end_date_picker);
        editTermButton = findViewById(R.id.edit_term_details);

        termTitleInput.setFocusable(false);
        termStartPicker.setEnabled(false);
        termEndPicker.setEnabled(false);

        RecyclerView recyclerView = findViewById(R.id.courses_in_terms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        final CoursesInTermAdapter adapter = new CoursesInTermAdapter(course -> {
            course.setTermId(0);
            courseViewModel.update(course);
        }, coursesInTerm);
        recyclerView.setAdapter(adapter);

        termStartPicker.setOnClickListener(view -> {
            termStartCalendar = Calendar.getInstance();
            int mYear = termStartCalendar.get(Calendar.YEAR);
            int mMonth = termStartCalendar.get(Calendar.MONTH);
            int mDay = termStartCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(TermDetailsActivity.this, (datePicker, year, month, day) -> {
                termStartCalendar.set(Calendar.YEAR, year);
                termStartCalendar.set(Calendar.MONTH, month);
                termStartCalendar.set(Calendar.DAY_OF_MONTH, day);
                termStartInput.setText(sdf.format(termStartCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        termEndPicker.setOnClickListener(view -> {
            termEndCalendar = Calendar.getInstance();
            int mYear = termEndCalendar.get(Calendar.YEAR);
            int mMonth = termEndCalendar.get(Calendar.MONTH);
            int mDay = termEndCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(TermDetailsActivity.this, (datePicker, year, month, day) -> {
                termEndCalendar.set(Calendar.YEAR, year);
                termEndCalendar.set(Calendar.MONTH, month);
                termEndCalendar.set(Calendar.DAY_OF_MONTH, day);
                termEndInput.setText(sdf.format(termEndCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        termTitleInput.setText(intent.getStringExtra(EXTRA_TERM_TITLE));
        Date startDate = (Date) intent.getSerializableExtra(EXTRA_TERM_START_DATE);
        Date endDate = (Date) intent.getSerializableExtra(EXTRA_TERM_END_DATE);
        termStartInput.setText(sdf.format(startDate));
        termEndInput.setText(sdf.format(endDate));

        setTitle(termTitleInput.getText() + " Details");

        editTermButton.setOnClickListener(view -> {
            termTitleInput.setFocusableInTouchMode(true);
            termStartPicker.setEnabled(true);
            termEndPicker.setEnabled(true);
            setTitle("Update: " + termTitleInput.getText());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveTerm:
                try {
                    saveTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTerm() throws ParseException{
        String title = termTitleInput.getText().toString();
        String startToParse = termStartInput.getText().toString();
        String endToParse = termEndInput.getText().toString();
        Date start = sdf.parse(startToParse);
        Date end = sdf.parse(endToParse);

        Intent intent = new Intent();
        int termId = getIntent().getIntExtra(EXTRA_TERM_ID, -1);
        if(termId != -1) {
            intent.putExtra(EXTRA_TERM_ID, termId);
        }

        intent.putExtra(EXTRA_TERM_TITLE, title);
        intent.putExtra(EXTRA_TERM_START_DATE, start);
        intent.putExtra(EXTRA_TERM_END_DATE, end);

        finish();

    }
}