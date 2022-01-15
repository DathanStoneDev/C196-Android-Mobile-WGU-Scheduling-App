package com.example.dathan_stone_c196_task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.repositories.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private LiveData<List<Course>> courses;
    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        courses = courseRepository.getCourses();
    }

    public LiveData<List<Course>> getCourses() { return courses; }

    public void insert(Course course) {
        courseRepository.addCourse(course);
    }

    public void update(Course course) {
        courseRepository.updateCourse(course);
    }

    public void delete(Course course) {
        courseRepository.deleteCourse(course);
    }
}
