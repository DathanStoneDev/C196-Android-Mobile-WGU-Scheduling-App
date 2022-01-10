package com.example.dathan_stone_c196_task.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.adapters.TermsAdapter;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.viewmodels.TermViewModel;

import java.util.List;

public class TermsActivity extends AppCompatActivity {

    private TermViewModel termViewModel;

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
        termViewModel.getTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTermList(terms);
            }
        });
        
        Button addTermButton = findViewById(R.id.addNewTermButton);
        addTermButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), AddTermActivity.class);
            startActivity(intent);
        });
    }


}