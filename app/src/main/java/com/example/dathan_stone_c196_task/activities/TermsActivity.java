package com.example.dathan_stone_c196_task.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

            if(code == RESULT_OK) {
                String title = data.getStringExtra(AddTermActivity.EXTRA_TITLE);
                String start = data.getStringExtra(AddTermActivity.EXTRA_START_DATE);
                String end = data.getStringExtra(AddTermActivity.EXTRA_END_DATE);

                Term term = new Term(title, start, end);
                termViewModel.insert(term);

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
            Intent intent = new Intent(view.getContext(), AddTermActivity.class);
            resultLauncher.launch(intent);
        });

    }


}