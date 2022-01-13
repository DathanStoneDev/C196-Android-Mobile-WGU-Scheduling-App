package com.example.dathan_stone_c196_task.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.DAO.TermDAO;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Term;

import java.util.List;

public class TermRepository {

    private TermDAO termDAO;
    private LiveData<List<Term>> terms;

    public TermRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        termDAO = db.termDAO();
        terms = termDAO.findAllTerms();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Term>> getTerms() {
        return terms;
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

    public void saveTerm(Term term) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            termDAO.saveTerm(term);
        });
    }

    public void deleteAllTerms() {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            termDAO.deleteAllTerms();
        });
    }

}
