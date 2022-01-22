package com.example.dathan_stone_c196_task.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.repositories.AssessmentRepository;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    private AssessmentRepository assessmentRepository;
    private LiveData<List<Assessment>> assessments;

    public AssessmentViewModel(Application application) {
        super(application);
        assessmentRepository = new AssessmentRepository(application);
        assessments = assessmentRepository.getAssessments();
    }

    public LiveData<List<Assessment>> getAssessments() {
        return assessments;
    }

    public void insert(Assessment assessment) {
        assessmentRepository.addAssessment(assessment);
    }

    public void update(Assessment assessment) { assessmentRepository.updateAssessment(assessment); }

    public void delete(Assessment assessment) { assessmentRepository.deleteAssessment(assessment); }
}
