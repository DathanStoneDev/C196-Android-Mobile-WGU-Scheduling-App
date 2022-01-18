package com.example.dathan_stone_c196_task.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.repositories.TermRepository;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TermRepository termRepository;
    private LiveData<List<Term>> terms;

    public TermViewModel(Application application) {
        super(application);
        termRepository = new TermRepository(application);
        terms = termRepository.getTerms();
    }

    public LiveData<List<Term>> getTerms() {
        return terms;
    }

    public void insert(Term term) {
        termRepository.addTerm(term);
    }

    public void update(Term term) { termRepository.updateTerm(term); }

    public void delete(Term term) { termRepository.deleteTerm(term); }

}
