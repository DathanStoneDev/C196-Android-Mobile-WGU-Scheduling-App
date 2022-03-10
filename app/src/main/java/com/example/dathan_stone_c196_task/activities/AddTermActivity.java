package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dathan_stone_c196_task.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddTermActivity extends AppCompatActivity {

    private EditText termTitleInput;
    private TextView termStartInput;
    private TextView termEndInput;
    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    Calendar startDateCalendar;
    Calendar endDateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        termTitleInput = findViewById(R.id.termTitleInput);
        termStartInput = findViewById(R.id.term_start_date);
        termEndInput = findViewById(R.id.term_end_date);
        startDateButton = findViewById(R.id.add_term_start_date);
        endDateButton = findViewById(R.id.add_term_end_date);


        startDateButton.setOnClickListener(view -> {
            startDateCalendar = Calendar.getInstance();
            int mYear = startDateCalendar.get(Calendar.YEAR);
            int mMonth = startDateCalendar.get(Calendar.MONTH);
            int mDay = startDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTermActivity.this, (datePicker, year, month, day) -> {
                startDateCalendar.set(Calendar.YEAR, year);
                startDateCalendar.set(Calendar.MONTH, month);
                startDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                termStartInput.setText(sdf.format(startDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });


        endDateButton.setOnClickListener(view -> {
            endDateCalendar = Calendar.getInstance();
            int mYear = endDateCalendar.get(Calendar.YEAR);
            int mMonth = endDateCalendar.get(Calendar.MONTH);
            int mDay = endDateCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddTermActivity.this, (datePicker, year, month, day) -> {
                endDateCalendar.set(Calendar.YEAR, year);
                endDateCalendar.set(Calendar.MONTH, month);
                endDateCalendar.set(Calendar.DAY_OF_MONTH, day);
                termEndInput.setText(sdf.format(endDateCalendar.getTime()));
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });




        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        setTitle("Add New Term");

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
                try {
                    saveNewTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNewTerm() throws ParseException {
        String title = termTitleInput.getText().toString();
        String startDateToParse = termStartInput.getText().toString();
        String endDateToParse = termEndInput.getText().toString();
        Date start = sdf.parse(startDateToParse);
        Date end = sdf.parse(endDateToParse);
        
        if(title.trim().isEmpty() || startDateToParse.trim().isEmpty() || endDateToParse.trim().isEmpty()) {
            Toast.makeText(this, "Please ensure all fields are filled out", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(TermDetailsActivity.EXTRA_TERM_TITLE, title);
        data.putExtra(TermDetailsActivity.EXTRA_TERM_START_DATE, start.getTime());
        data.putExtra(TermDetailsActivity.EXTRA_TERM_END_DATE, end.getTime());

        setResult(RESULT_OK, data);
        finish();
    }
}