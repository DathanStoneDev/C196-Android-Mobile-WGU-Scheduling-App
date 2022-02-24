package com.example.dathan_stone_c196_task.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.DAO.TermDAO;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;

import java.util.List;
import java.util.Map;

public class TermRepository {

    private TermDAO termDAO;
    private LiveData<List<Term>> terms;
    private LiveData<List<TermWithCourses>> termDetails;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDAO = db.termDAO();
        terms = termDAO.findAllTerms();
        termDetails = termDAO.findAllTermDetails();

        try {
            Thread.sleep(1000);
            System.out.println("Details" + termDetails.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Term>> getTerms() {
        return terms;
    }

    public LiveData<List<TermWithCourses>> getTermDetails() {
        return termDetails;
    }


    public void addTerm(Term term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            termDAO.saveTerm(term);
        });
    }

    public void updateTerm(Term term) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            termDAO.updateTerm(term);
        });
    }

    public void deleteTerm(Term term) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            termDAO.deleteTerm(term);
        });
    }
}
