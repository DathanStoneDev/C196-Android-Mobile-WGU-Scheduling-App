package com.example.dathan_stone_c196_task.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.AssessmentsInCourseAdapter;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.utilities.CourseAlertReceiver;
import com.example.dathan_stone_c196_task.viewmodels.AssessmentViewModel;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TITLE";
    public static final String EXTRA_COURSE_START_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_END_DATE";
    public static final String EXTRA_COURSE_STATUS = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_STATUS";
    public static final String EXTRA_ASSESSMENTS_IN_COURSE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENTS_IN_COURSE";
    public static final String EXTRA_COURSE_TERM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_NOTES = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_NOTES";
    public static final String EXTRA_COURSE_INSTRUCTOR_NAME = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_IN_NAME";
    public static final String EXTRA_COURSE_INSTRUCTOR_PHONE = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_IN_PHONE";
    public static final String EXTRA_COURSE_INSTRUCTOR_EMAIL = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_IN_EMAIL";
    public static final String EXTRA_COURSE_ALARM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_COURSE_ALARM_ID";


    private EditText courseTitleInput;
    private TextView courseStartInput;
    private TextView courseEndInput;
    private Spinner courseStatusSpinner;
    private Spinner courseTermSpinner;
    private EditText courseNotes;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private EditText instructorNameDetail;
    private EditText instructorPhoneDetail;
    private EditText instructorEmailDetail;
    private Button editButton;
    private Calendar startDateCalendar = Calendar.getInstance();
    private Calendar endDateCalendar = Calendar.getInstance();
    private List<Assessment> assessmentsInCourse = new ArrayList<>();
    private List<Term> allTerms = new ArrayList<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private AssessmentViewModel assessmentViewModel;
    private TermViewModel termViewModel;
    private Calendar alarmCalander;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle("Course Details");

        createNotificationChanel();

        Intent intent = getIntent();

        courseTitleInput = findViewById(R.id.course_assessments_details_title);
        courseStartInput = findViewById(R.id.course_details_start_date);
        courseEndInput = findViewById(R.id.course_details_end_date);
        courseTermSpinner = findViewById(R.id.course_term_details);
        courseStatusSpinner = findViewById(R.id.course_status_details);
        courseNotes = findViewById(R.id.course_notes_details);
        instructorNameDetail = findViewById(R.id.instructor_name);
        instructorPhoneDetail = findViewById(R.id.instructor_phone_number);
        instructorEmailDetail = findViewById(R.id.instructor_email);
        startDateButton = findViewById(R.id.edit_term_start_date_picker);
        endDateButton = findViewById(R.id.edit_term_end_date_picker);
        assessmentsInCourse = new ArrayList<>(intent.getParcelableArrayListExtra(EXTRA_ASSESSMENTS_IN_COURSE));
        editButton = findViewById(R.id.edit_course_details);

        Date startDate = (Date)intent.getSerializableExtra(EXTRA_COURSE_START_DATE);
        Date endDate = (Date)intent.getSerializableExtra(EXTRA_COURSE_END_DATE);
        int courseAlarmID = intent.getIntExtra(EXTRA_COURSE_ALARM_ID, -1);
        cancelAlert(courseAlarmID);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.course_types, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);
        courseStatusSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<Term> courseTermAdapter = new ArrayAdapter<Term>(this, android.R.layout.simple_spinner_dropdown_item, allTerms);
        courseTermAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseTermSpinner.setAdapter(courseTermAdapter);
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTerms().observe(this, terms -> {
            allTerms.addAll(terms);
            courseTermAdapter.notifyDataSetChanged();
        });

        RecyclerView recyclerView = findViewById(R.id.assessments_in_course);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        final AssessmentsInCourseAdapter adapter = new AssessmentsInCourseAdapter(assessment -> {
            assessment.setCourseId(0);
            assessmentViewModel.update(assessment);
        }, assessmentsInCourse);
        recyclerView.setAdapter(adapter);

        courseTitleInput.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));
        courseStartInput.setText(sdf.format(startDate));
        courseEndInput.setText(sdf.format(endDate));
        instructorNameDetail.setText(intent.getStringExtra(EXTRA_COURSE_INSTRUCTOR_NAME));
        instructorPhoneDetail.setText(intent.getStringExtra(EXTRA_COURSE_INSTRUCTOR_PHONE));
        instructorEmailDetail.setText(intent.getStringExtra(EXTRA_COURSE_INSTRUCTOR_EMAIL));
        courseNotes.setText(intent.getStringExtra(EXTRA_COURSE_NOTES));
        courseStatusSpinner.setSelection(statusAdapter.getPosition(intent.getStringExtra(EXTRA_COURSE_STATUS)));
        courseTermSpinner.setSelection(statusAdapter.getPosition(intent.getStringExtra(EXTRA_COURSE_TERM_ID)));

        courseTitleInput.setFocusable(false);
        startDateButton.setEnabled(false);
        endDateButton.setEnabled(false);
        courseStatusSpinner.setEnabled(false);
        courseTermSpinner.setEnabled(false);
        courseNotes.setFocusable(false);
        instructorNameDetail.setFocusable(false);
        instructorPhoneDetail.setFocusable(false);
        instructorEmailDetail.setFocusable(false);

        editButton.setOnClickListener(view -> {
            courseTitleInput.setFocusableInTouchMode(true);
            startDateButton.setEnabled(true);
            endDateButton.setEnabled(true);
            courseStatusSpinner.setEnabled(true);
            courseTermSpinner.setEnabled(true);
            courseNotes.setFocusableInTouchMode(true);
            instructorNameDetail.setFocusableInTouchMode(true);
            instructorPhoneDetail.setFocusableInTouchMode(true);
            instructorEmailDetail.setFocusableInTouchMode(true);
        });

        startDateButton.setOnClickListener(view -> {
            startDateCalendar = Calendar.getInstance();
            int mYear = startDateCalendar.get(Calendar.YEAR);
            int mMonth = startDateCalendar.get(Calendar.MONTH);
            int mDay = startDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CourseDetailsActivity.this, (datePicker, year, month, day) -> {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, month);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                courseStartInput.setText(sdf.format(startDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        endDateButton.setOnClickListener(view -> {
            endDateCalendar = Calendar.getInstance();
            int mYear = endDateCalendar.get(Calendar.YEAR);
            int mMonth = endDateCalendar.get(Calendar.MONTH);
            int mDay = endDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(CourseDetailsActivity.this, (datePicker, year, month, day) -> {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, month);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                courseEndInput.setText(sdf.format(endDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
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
        menuInflater.inflate(R.menu.add_edit_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveEditedCourse:
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

    private void saveCourse () throws ParseException {
        String title = courseTitleInput.getText().toString();
        String startDateToParse = courseStartInput.getText().toString();
        String endDateToParse = courseEndInput.getText().toString();
        Date start = sdf.parse(startDateToParse);
        Date end = sdf.parse(endDateToParse);
        String status = courseStatusSpinner.getSelectedItem().toString();
        String note = courseNotes.getText().toString();
        String instructorName = instructorNameDetail.getText().toString();
        String instructorPhone = instructorPhoneDetail.getText().toString();
        String instructorEmail = instructorEmailDetail.getText().toString();
        String courseStatus = courseStatusSpinner.getSelectedItem().toString();
        int termId = ((Term) courseTermSpinner.getSelectedItem()).getId();

        int courseAlarmId = termId;

        alarmCalander = Calendar.getInstance();
        alarmCalander.setTime(start);
        alarmCalander.set(Calendar.HOUR_OF_DAY, 8);

        setAlert(courseAlarmId);

        Intent data = new Intent();
        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if(courseId != -1) {
            data.putExtra(EXTRA_COURSE_ID, courseId);
        }

        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE, title);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, start.getTime());
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, end.getTime());
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, status);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES, note);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, termId);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_NAME, instructorName);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_PHONE, instructorPhone);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_EMAIL, instructorEmail);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, termId);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, courseStatus);

        setResult(RESULT_OK, data);
        finish();
    }

    //Creates the notification channel to connect to the CourseAlertReceiver Notification builder
    private void createNotificationChanel() {
        NotificationChannel channel = new NotificationChannel("course_channel", "course_channel", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void setAlert(int courseAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CourseAlertReceiver.class);
        intent.putExtra(CourseDetailsActivity.EXTRA_COURSE_ALARM_ID, courseAlarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, courseAlarmId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmCalander.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlert(int courseAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), CoursesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), courseAlarmId, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
