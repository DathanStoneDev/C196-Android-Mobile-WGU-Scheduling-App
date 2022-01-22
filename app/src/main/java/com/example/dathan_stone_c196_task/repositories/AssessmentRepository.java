package com.example.dathan_stone_c196_task.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.DAO.AssessmentDAO;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Assessment;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDAO assessmentDAO;
    private LiveData<List<Assessment>> assessments;

    public AssessmentRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        assessmentDAO = db.assessmentDAO();
        assessments = assessmentDAO.findAllAssessments();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Assessment>> getAssessments() { return assessments; }

    public void addAssessment(Assessment assessment) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            assessmentDAO.saveAssessment(assessment);
        });
    }

    public void updateAssessment(Assessment assessment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            assessmentDAO.updateAssessment(assessment);
        });
    }

    public void deleteAssessment(Assessment assessment) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            assessmentDAO.deleteAssessment(assessment);
        });
    }
}
