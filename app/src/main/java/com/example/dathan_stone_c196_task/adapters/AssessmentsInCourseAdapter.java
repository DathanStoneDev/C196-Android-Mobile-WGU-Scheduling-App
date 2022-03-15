package com.example.dathan_stone_c196_task.adapters;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_in_course_item, parent, false);
        return new AssessmentCourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsInCourseAdapter.AssessmentCourseHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        holder.textViewTitle.setText(assessment.getAssessmentTitle());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    class AssessmentCourseHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private ImageButton removeAssessmentButton;

        public AssessmentCourseHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.assessment_in_course_title);
            removeAssessmentButton = itemView.findViewById(R.id.remove_assessment_button);
            removeAssessmentButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.removeAssessmentFromCourse(assessments.get(position));
                removeAssessment(position);
            });
        }
    }

    public void removeAssessment(int position) {
        assessments.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, assessments.size());
    }

    public interface OnItemClickListener {
        void removeAssessmentFromCourse(Assessment assessment);
    }
}
