package com.example.dathan_stone_c196_task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEditAssessmentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ASSESSMENT_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TITLE";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_START = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_START";
    public static final String EXTRA_ASSESSMENT_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_ALARM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_ALARM_ID";

    private EditText assessmentTitleInput;
    private Spinner assessmentTypeSpinner;
    private Spinner courseSpinner;
    private ArrayList<Course> courses = new ArrayList<>();
    private CourseViewModel courseViewModel;
    private Button editButton;
    private EditText popUpButton;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessments);

        assessmentTitleInput = findViewById(R.id.assessmentTitleInput);
        assessmentTypeSpinner = findViewById(R.id.assessment_types_spinner);
        courseSpinner = findViewById(R.id.course_assessment_spinner);
        editButton = findViewById(R.id.edit_assessment_button);
        popUpButton = findViewById(R.id.assessment_date_picker);
        popUpButton.setInputType(InputType.TYPE_NULL);

        Intent intent = getIntent();

        final Calendar calendar = Calendar.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.assessment_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assessmentTypeSpinner.setAdapter(adapter);
        assessmentTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Course> courseTermAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_dropdown_item, courses);
        courseTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseTermAdapter);
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourses().observe(this, addCourses -> {
            courses.addAll(addCourses);
            courseTermAdapter.notifyDataSetChanged();
        });

        if(intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Assessment Details");
            assessmentTitleInput.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TITLE));
            assessmentTypeSpinner.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE)));
            courseSpinner.setSelection(adapter.getPosition(intent.getStringExtra(EXTRA_ASSESSMENT_COURSE_ID)));
            System.out.println(intent.getIntExtra(EXTRA_ASSESSMENT_COURSE_ID, -1));
            assessmentTitleInput.setFocusable(false);
            assessmentTypeSpinner.setEnabled(false);
            courseSpinner.setEnabled(false);
            editButton.setVisibility(View.VISIBLE);
            Date date1 = (Date)intent.getSerializableExtra(EXTRA_ASSESSMENT_START);
            calendar.setTime(date1);
            popUpButton.setText(sdf.format(calendar.getTime()));
            popUpButton.setEnabled(false);

        } else {
            setTitle("Add Assessment");
            editButton.setVisibility(View.INVISIBLE);
            //This still allows you to edit the text so that needs to be fixed.
            popUpButton.setText("Please Select A Date!");
        }

        popUpButton.setOnClickListener(view -> {

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

           DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditAssessmentsActivity.this, (datePicker, year, month, day) -> {
               calendar.set(Calendar.YEAR, year);
               calendar.set(Calendar.MONTH, month);
               calendar.set(Calendar.DAY_OF_MONTH, day);
               popUpButton.setText(sdf.format(calendar.getTime()));
           }, mYear, mMonth, mDay);
           datePickerDialog.show();
        });

        editButton.setOnClickListener(view -> {
            assessmentTitleInput.setFocusableInTouchMode(true);
            assessmentTypeSpinner.setEnabled(true);
            courseSpinner.setEnabled(true);
            popUpButton.setEnabled(true);
            setTitle("Update Assessment");
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_assessment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveAssessment:
                try {
                    saveAssessment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveAssessment() throws ParseException {
        String title = assessmentTitleInput.getText().toString();
        String type = assessmentTypeSpinner.getSelectedItem().toString();
        Course course = (Course) courseSpinner.getSelectedItem();
        String date1 = popUpButton.getText().toString();
        Date date = sdf.parse(date1);
        int courseId = course.getCourseId();

        Intent data = new Intent();
        int assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if(assessmentId != -1) {
            data.putExtra(EXTRA_ASSESSMENT_ID, assessmentId);
        }
        data.putExtra(EXTRA_ASSESSMENT_START, date.getTime());
        data.putExtra(EXTRA_ASSESSMENT_TITLE, title);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);
        data.putExtra(EXTRA_ASSESSMENT_COURSE_ID, courseId);
        setResult(RESULT_OK, data);
        finish();
    }
}