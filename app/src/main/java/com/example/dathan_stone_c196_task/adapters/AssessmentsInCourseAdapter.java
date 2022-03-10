package com.example.dathan_stone_c196_task.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.entities.Assessment;

import java.util.List;

public class AssessmentsInCourseAdapter extends RecyclerView.Adapter<AssessmentsInCourseAdapter.AssessmentCourseHolder> {

    private AssessmentsInCourseAdapter.OnItemClickListener listener;
    private List<Assessment> assessments;

    public AssessmentsInCourseAdapter(OnItemClickListener listener, List<Assessment> assessments) {
        this.listener = listener;
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public AssessmentsInCourseAdapter.AssessmentCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsInCourseAdapter.AssessmentCourseHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AssessmentCourseHolder extends RecyclerView.ViewHolder {

        public AssessmentCourseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void removeAssessment(int position) {

    }

    public interface OnItemClickListener {
        void removeAssessmentFromCourse(Assessment assessment);
    }
}
