package com.example.dathan_stone_c196_task.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;
import com.example.dathan_stone_c196_task.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private List<Course> courses = new ArrayList<>();
    private List<CourseWithAssessments> courseDetailList = new ArrayList<>();
    private CoursesAdapter.OnItemClickListener listener;

    public CoursesAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setCourseDetailList(List<CourseWithAssessments> courseDetailList) {
        this.courseDetailList = courseDetailList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        CourseWithAssessments details = courseDetailList.get(position);
        holder.textViewTitle.setText(details.course.getTitle());
    }

    @Override
    public int getItemCount() {
        return courseDetailList.size();
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private ImageButton detailView;
        private ImageButton deleteView;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.course_title);
            deleteView= itemView.findViewById(R.id.delete_course_button);
            detailView = itemView.findViewById(R.id.course_details);


            deleteView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.deleteCourse(courseDetailList.get(position));
            });

            detailView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.detailsForCourse(courseDetailList.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void deleteCourse(CourseWithAssessments delete);
        void detailsForCourse(CourseWithAssessments details);
    }
}
