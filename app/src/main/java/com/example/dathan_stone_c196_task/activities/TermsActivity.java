package com.example.dathan_stone_c196_task.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.TermsAdapter;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TermsActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            Intent data = result.getData();

            if(code == RESULT_OK && data.hasExtra(TermDetailsActivity.EXTRA_TERM_ID)) {
                int id = data.getIntExtra(TermDetailsActivity.EXTRA_TERM_ID, -1);
                String title = data.getStringExtra(TermDetailsActivity.EXTRA_TERM_TITLE);
                Date startDate = new Date(data.getLongExtra(TermDetailsActivity.EXTRA_TERM_START_DATE, -1));
                Date endDate = new Date(data.getLongExtra(TermDetailsActivity.EXTRA_TERM_END_DATE, -1));

                Term term = new Term(title, startDate, endDate);
                term.setId(id);
                termViewModel.update(term);
            } else if (code == RESULT_OK && !data.hasExtra(TermDetailsActivity.EXTRA_TERM_ID)){
                String title = data.getStringExtra(TermDetailsActivity.EXTRA_TERM_TITLE);
                Date startDate = new Date(data.getLongExtra(TermDetailsActivity.EXTRA_TERM_START_DATE, -1));
                Date endDate = new Date(data.getLongExtra(TermDetailsActivity.EXTRA_TERM_END_DATE, -1));

                Term term = new Term(title, startDate, endDate);
                termViewModel.insert(term);
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
                if(delete.courses.size() > 0) {
                    Toast.makeText(TermsActivity.this, "Can not delete because there are courses in this term", Toast.LENGTH_SHORT).show();
                } else {
                    termViewModel.delete(delete.term);
                }
            }

            @Override
            public void detailsForTerm(TermWithCourses details) {

                Intent intent = new Intent(TermsActivity.this, TermDetailsActivity.class);
                intent.putExtra(TermDetailsActivity.EXTRA_TERM_ID, details.term.getId());
                intent.putExtra(TermDetailsActivity.EXTRA_TERM_TITLE, details.term.getTitle());
                intent.putExtra(TermDetailsActivity.EXTRA_TERM_START_DATE, details.term.getStartDate());
                intent.putExtra(TermDetailsActivity.EXTRA_TERM_END_DATE, details.term.getEndDate());
                List courseTerms = details.getCourses();

                intent.putParcelableArrayListExtra(TermDetailsActivity.EXTRA_COURSES_IN_TERM, (ArrayList<? extends Parcelable>) courseTerms);

                resultLauncher.launch(intent);

            }
        });
        recyclerView.setAdapter(termsAdapter);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermDetails().observe(this, terms -> termsAdapter.setTermDetailsList(terms));
        
        FloatingActionButton addTermButton = findViewById(R.id.addNewTermButton);
        addTermButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddTermActivity.class);
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