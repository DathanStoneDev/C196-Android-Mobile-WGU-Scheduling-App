package com.example.dathan_stone_c196_task.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.dathan_stone_c196_task.utilities.CourseAlertReceiver;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

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
    private EditText instructorNameInput;
    private EditText instructorPhoneInput;
    private EditText instructorEmailInput;
    Calendar startDateCalendar;
    Calendar endDateCalendar;
    private TermViewModel termViewModel;
    private ArrayList<Term> allTerms = new ArrayList<>();
    private AlarmManager alarmManager;
    Calendar startAlarmCalender;
    Calendar endAlarmCalender;

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
        courseStatusSpinner = findViewById(R.id.course_status_spinner);
        courseTermSpinner = findViewById(R.id.course_term_spinner);
        courseNotes = findViewById(R.id.course_notes);
        instructorNameInput = findViewById(R.id.instructor_name_input);
        instructorPhoneInput = findViewById(R.id.instructor_phone_number_input);
        instructorEmailInput = findViewById(R.id.instructor_email_input);



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

        createNotificationChanel();
    }

    //Not used.
   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String selectedType = parent.getItemAtPosition(i).toString();

        Toast.makeText(this, selectedType, Toast.LENGTH_SHORT).show();

    }

    //Not used
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Creates an Options Menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    //Switch statement to determine what action to perform if a menu item is clicked.
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

    //Saves a new course and sends to the CourseActivity ActivityResultLauncher.
    private void saveCourse() throws ParseException {

        String title = courseTitleInput.getText().toString();
        String startDateToParse = courseStart.getText().toString();
        String endDateToParse = courseEnd.getText().toString();
        Date start = sdf.parse(startDateToParse);
        Long longStart = start.getTime();
        Date end = sdf.parse(endDateToParse);
        String status = courseStatusSpinner.getSelectedItem().toString();
        String note = courseNotes.getText().toString();
        String instructorName = instructorNameInput.getText().toString();
        String instructorPhone = instructorPhoneInput.getText().toString();
        String instructorEmail = instructorEmailInput.getText().toString();
        String courseStatus = courseStatusSpinner.getSelectedItem().toString();
        int termId = ((Term) courseTermSpinner.getSelectedItem()).getId();
        //Make Random
        Random randomId = new Random();
        int startCourseAlarmId = randomId.nextInt(50000);
        int endCourseAlarmId = randomId.nextInt(50000);

        //Sets an alarm automatically upon saving.
        startAlarmCalender = Calendar.getInstance();
        startAlarmCalender.setTime(start);
        startAlarmCalender.set(Calendar.HOUR_OF_DAY, 17);
        startAlarmCalender.set(Calendar.MINUTE, 31);
        setStartAlert(startCourseAlarmId);

        endAlarmCalender = Calendar.getInstance();
        endAlarmCalender.setTime(end);
        endAlarmCalender.set(Calendar.HOUR_OF_DAY, 17);
        endAlarmCalender.set(Calendar.MINUTE, 32);
        setEndAlert(endCourseAlarmId);

        //Creates the intent and adds the data that will be sent over to CourseActivity.
        Intent data = new Intent();
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TITLE, title);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_START_DATE, longStart);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_END_DATE, end);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, status);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_NOTES, note);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, termId);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_NAME, instructorName);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_PHONE, instructorPhone);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_INSTRUCTOR_EMAIL, instructorEmail);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_TERM_ID, termId);
        data.putExtra(CourseDetailsActivity.EXTRA_COURSE_STATUS, courseStatus);
        data.putExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, startCourseAlarmId);
        data.putExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, endCourseAlarmId);
        setResult(RESULT_OK, data);

        //send email of notes
        if (!note.trim().isEmpty()) {
            sendNotesToEmail(note, title);
        }
        finish();
    }

    //Creates the notification channel to connect to the CourseAlertReceiver Notification builder
    private void createNotificationChanel() {
        NotificationChannel channel = new NotificationChannel("course_channel", "course_channel", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    //Sets an alert for the course start date
    private void setStartAlert(int startCourseAlarmId) {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CourseAlertReceiver.class);
        intent.putExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, startCourseAlarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, startCourseAlarmId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, startAlarmCalender.getTimeInMillis(), pendingIntent);
        System.out.println(startCourseAlarmId);
    }

    //Sets an alert for course end date.
    private void setEndAlert(int endCourseAlarmId) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CourseAlertReceiver.class);
        intent.putExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, endCourseAlarmId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, endCourseAlarmId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endAlarmCalender.getTimeInMillis(), pendingIntent);
        System.out.println(endCourseAlarmId);
    }

    //Sends notes in an email
    private void sendNotesToEmail(String notes, String title) {
        Intent emailInfo = new Intent(Intent.ACTION_SEND);
        emailInfo.setData(Uri.parse("mailto:"));
        emailInfo.setType("text/plain");
        emailInfo.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        emailInfo.putExtra(Intent.EXTRA_SUBJECT, "Class Notes: " + title);
        emailInfo.putExtra(Intent.EXTRA_TEXT, notes);

        try {
            startActivity(Intent.createChooser(emailInfo, "Send Notes to..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }
}
