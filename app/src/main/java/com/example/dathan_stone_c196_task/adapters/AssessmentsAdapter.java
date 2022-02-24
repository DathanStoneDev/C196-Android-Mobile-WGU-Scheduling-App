package com.example.dathan_stone_c196_task.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Assessment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentHolder> {

    private List<Assessment> assessments = new ArrayList<>();
    private AssessmentsAdapter.OnItemClickListener listener;

    public AssessmentsAdapter( OnItemClickListener listener) {
        this.listener = listener;

    }

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        Assessment assessment = assessments.get(position);
        System.out.println("Position: " + position);
        holder.textView.setText(assessment.getAssessmentTitle());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }


    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

     class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageButton detailView;
        private ImageButton deleteView;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.assessment_title);
            detailView = itemView.findViewById(R.id.assessment_details);
            deleteView = itemView.findViewById(R.id.delete_assessment);

            deleteView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.DeleteItem(assessments.get(position));
            });

            detailView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.DetailsForItem(assessments.get(position));
            });
        }
    }

    //Set different methods in this interface
    public interface OnItemClickListener {
        void DeleteItem(Assessment assessment);
        void DetailsForItem(Assessment assessment);
    }


}
