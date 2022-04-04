package com.example.dathan_stone_c196_task.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.utilities.AssessmentAlertReceiver;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddEditAssessmentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ASSESSMENT_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_TITLE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TITLE";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_START = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_START";
    public static final String EXTRA_ASSESSMENT_END= "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_END";
    public static final String EXTRA_ASSESSMENT_COURSE_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_COURSE_ID";
    public static final String EXTRA_ASSESSMENT_START_ALARM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_START_ALARM_ID";
    public static final String EXTRA_ASSESSMENT_END_ALARM_ID = "com.example.dathan_stone_c196_task.activities.EXTRA_ASSESSMENT_END_ALARM_ID";

    private EditText assessmentTitleInput;
    private Spinner assessmentTypeSpinner;
    private Spinner courseSpinner;
    private ArrayList<Course> courses = new ArrayList<>();
    private CourseViewModel courseViewModel;
    private Button editButton;
    private ImageButton startDatePicker;
    private ImageButton endDatePicker;
    private TextView startDateView;
    private TextView endDateView;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private Calendar startCalendar;
    private Calendar endCalendar;
    private Calendar startAlarmCalender;
    private Calendar endAlarmCalender;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessments);

        createNotificationChanel();

        assessmentTitleInput = findViewById(R.id.assessmentTitleInput);
        assessmentTypeSpinner = findViewById(R.id.assessment_types_spinner);
        startDateView = findViewById(R.id.assessment_start_date_view);
        endDateView = findViewById(R.id.assessment_end_date_view);
        startDatePicker = findViewById(R.id.assessment_start_date_picker);
        endDatePicker = findViewById(R.id.assessment_end_date_picker);
        courseSpinner = findViewById(R.id.course_assessment_spinner);
        editButton = findViewById(R.id.edit_assessment_button);


        Intent intent = getIntent();

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
            assessmentTitleInput.setFocusable(false);
            assessmentTypeSpinner.setEnabled(false);
            courseSpinner.setEnabled(false);
            startDatePicker.setEnabled(false);
            endDatePicker.setEnabled(false);
            editButton.setVisibility(View.VISIBLE);
            Date startDate = (Date)intent.getSerializableExtra(EXTRA_ASSESSMENT_START);
            Date endDate = (Date)intent.getSerializableExtra(EXTRA_ASSESSMENT_END);
            startDateView.setText(sdf.format(startDate));
            endDateView.setText(sdf.format(endDate));
            int startAlarmId = intent.getIntExtra(EXTRA_ASSESSMENT_START_ALARM_ID, -1);
            int endAlarmId = intent.getIntExtra(EXTRA_ASSESSMENT_END_ALARM_ID, -1);
            cancelAlert(startAlarmId);
            cancelAlert(endAlarmId);

        } else {
            setTitle("Add Assessment");
            editButton.setVisibility(View.INVISIBLE);
        }

        startDatePicker.setOnClickListener(view -> {
            startCalendar = Calendar.getInstance();
            int mYear = startCalendar.get(Calendar.YEAR);
            int mMonth = startCalendar.get(Calendar.MONTH);
            int mDay = startCalendar.get(Calendar.DAY_OF_MONTH);

           DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditAssessmentsActivity.this, (datePicker, year, month, day) -> {
               startCalendar.set(Calendar.YEAR, year);
               startCalendar.set(Calendar.MONTH, month);
               startCalendar.set(Calendar.DAY_OF_MONTH, day);
               startDateView.setText(sdf.format(startCalendar.getTime()));
           }, mYear, mMonth, mDay);
           datePickerDialog.show();
        });

        endDatePicker.setOnClickListener(view -> {
            endCalendar = Calendar.getInstance();
            int mYear = endCalendar.get(Calendar.YEAR);
            int mMonth = endCalendar.get(Calendar.MONTH);
            int mDay = endCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditAssessmentsActivity.this, (datePicker, year, month, day) -> {
                endCalendar.set(Calendar.YEAR, year);
                endCalendar.set(Calendar.MONTH, month);
                endCalendar.set(Calendar.DAY_OF_MONTH, day);
                endDateView.setText(sdf.format(endCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        editButton.setOnClickListener(view -> {
            assessmentTitleInput.setFocusableInTouchMode(true);
            assessmentTypeSpinner.setEnabled(true);
            courseSpinner.setEnabled(true);
            startDatePicker.setEnabled(true);
            endDatePicker.setEnabled(true);
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
        String startDateToParse = startDateView.getText().toString();
        String endDateToParse = endDateView.getText().toString();
        Date startDate = sdf.parse(startDateToParse);
        Date endDate = sdf.parse(endDateToParse);
        int courseId = course.getCourseId();

        Random random = new Random();
        int startAssessmentAlarmId = random.nextInt(50000);
        int endAssessmentAlarmId = random.nextInt(50000);

        //sets start date alarm
        startAlarmCalender = Calendar.getInstance();
        startAlarmCalender.setTime(startDate);
        startAlarmCalender.set(Calendar.HOUR_OF_DAY, 15);
        startAlarmCalender.set(Calendar.MINUTE, 29);
        setStartAlert(startAssessmentAlarmId);
        System.out.println(startAlarmCalender.getTime().toString());

        //sets end date alarm
        endAlarmCalender = Calendar.getInstance();
        endAlarmCalender.setTime(endDate);
        endAlarmCalender.set(Calendar.HOUR_OF_DAY, 15);
        endAlarmCalender.set(Calendar.MINUTE, 30);
        setEndAlert(endAssessmentAlarmId);
        System.out.println(endAlarmCalender.getTime().toString());


        Intent data = new Intent();

        int assessmentId = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if(assessmentId != -1) {
            data.putExtra(EXTRA_ASSESSMENT_ID, assessmentId);
        }

        data.putExtra(EXTRA_ASSESSMENT_START, startDate.getTime());
        data.putExtra(EXTRA_ASSESSMENT_END, endDate.getTime());
        data.putExtra(EXTRA_ASSESSMENT_TITLE, title);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);
        data.putExtra(EXTRA_ASSESSMENT_COURSE_ID, courseId);
        data.putExtra(EXTRA_ASSESSMENT_START_ALARM_ID, startAssessmentAlarmId);
        data.putExtra(EXTRA_ASSESSMENT_END_ALARM_ID, endAssessmentAlarmId);
        setResult(RESULT_OK, data);


        finish();
    }

    private void createNotificationChanel() {
        NotificationChannel channel = new NotificationChannel("assess", "assess", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void setStartAlert(int assessmentAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AssessmentAlertReceiver.class);
        intent.putExtra(EXTRA_ASSESSMENT_START_ALARM_ID, assessmentAlarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, assessmentAlarmId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startAlarmCalender.getTimeInMillis(), pendingIntent);
        System.out.println(assessmentAlarmId);
        System.out.println("FOR YOU: " + intent.getIntExtra(EXTRA_ASSESSMENT_START_ALARM_ID, -1));
    }

    private void setEndAlert(int assessmentAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AssessmentAlertReceiver.class);
        intent.putExtra(EXTRA_ASSESSMENT_END_ALARM_ID, assessmentAlarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, assessmentAlarmId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endAlarmCalender.getTimeInMillis(), pendingIntent);
        System.out.println(assessmentAlarmId);
    }

    private void cancelAlert(int assessmentAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), AssessmentsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), assessmentAlarmId, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}