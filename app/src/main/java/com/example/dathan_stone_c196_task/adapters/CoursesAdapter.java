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
import com.example.dathan_stone_c196_task.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseHolder> {

    private List<Course> courses = new ArrayList<>();
    private CoursesAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getTitle());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourseList(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private ImageButton detailView;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.course_title);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.OnItemClick(courses.get(position));
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Course course);
    }

    public void setOnItemClickListener(CoursesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
