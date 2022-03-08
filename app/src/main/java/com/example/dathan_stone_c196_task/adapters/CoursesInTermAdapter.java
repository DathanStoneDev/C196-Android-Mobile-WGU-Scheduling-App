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

import java.util.List;

public class CoursesInTermAdapter extends RecyclerView.Adapter<CoursesInTermAdapter.CourseTermHolder> {

    private CoursesInTermAdapter.OnItemClickListener listener;
    private List<Course> courses;

    public CoursesInTermAdapter(OnItemClickListener listener, List<Course> courses) {
        this.listener = listener;
        this.courses = courses;
    }

    @NonNull
    @Override
    public CoursesInTermAdapter.CourseTermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_in_term_item, parent, false);
        return new CourseTermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesInTermAdapter.CourseTermHolder holder, int position) {
        Course course = courses.get(position);
        holder.textViewTitle.setText(course.getTitle());
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    class CourseTermHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private ImageButton removeCourseButton;

        public CourseTermHolder(@NonNull View itemView) {
            super(itemView);


            textViewTitle = itemView.findViewById(R.id.course_in_term_title);
            removeCourseButton = itemView.findViewById(R.id.remove_course_from_term);
            removeCourseButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                listener.removeCourseFromTerm(courses.get(position));
                removeCourse(position);
        });

        }
    }

    public void removeCourse(int position) {
        courses.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, courses.size());
    }

    public interface OnItemClickListener {
        void removeCourseFromTerm(Course course);
    }
}
