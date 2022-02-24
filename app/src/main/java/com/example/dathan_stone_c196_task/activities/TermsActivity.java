package com.example.dathan_stone_c196_task.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.TermsAdapter;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;
import com.example.dathan_stone_c196_task.viewmodels.CourseViewModel;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TermsActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();
            String title;
            String start;
            String end;
            int id;

            //Adds term
            if(code == RESULT_OK && !data.hasExtra(AddEditTermActivity.EXTRA_ID)) {

                title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
                start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
                end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);

                Term term = new Term(title, start, end);
                termViewModel.insert(term);
            //Updates term
            } else if (code == RESULT_OK && data.hasExtra(AddEditTermActivity.EXTRA_ID)) {
                id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);

                //need if statement to check extra id
                title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
                start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
                end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);
                Term term = new Term(title, start, end);
                term.setId(id);
                termViewModel.update(term);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        RecyclerView recyclerView = findViewById(R.id.terms_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TermsAdapter termsAdapter = new TermsAdapter(new TermsAdapter.OnItemClickListener() {

            @Override
            public void deleteTerm(TermWithCourses delete) {

            }

            @Override
            public void detailsForTerm(TermWithCourses details) {

            }

            /*@Override
            public void detailsForTerm(Term term) {
                Intent intent = new Intent(TermsActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStartDate());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEndDate());

                resultLauncher.launch(intent);
            } */
        });
        recyclerView.setAdapter(termsAdapter);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermDetails().observe(this, terms -> termsAdapter.setTermDetailsList(terms));
        
        FloatingActionButton addTermButton = findViewById(R.id.addNewTermButton);
        addTermButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddEditTermActivity.class);
            resultLauncher.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.term_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete_all_terms:
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}