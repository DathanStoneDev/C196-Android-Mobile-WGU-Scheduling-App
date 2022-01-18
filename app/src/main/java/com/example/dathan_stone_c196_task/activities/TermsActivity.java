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
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //displays items below each other.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TermsAdapter adapter = new TermsAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTerms().observe(this, terms -> adapter.setTermList(terms));
        
        FloatingActionButton addTermButton = findViewById(R.id.addNewTermButton);
        addTermButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddEditTermActivity.class);
            resultLauncher.launch(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TermsActivity.this, "Term Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermsActivity.this, AddEditTermActivity.class);
            intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getId());
            intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTitle());
            intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getStartDate());
            intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getEndDate());

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