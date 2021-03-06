package com.example.dathan_stone_c196_task.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;
import com.example.dathan_stone_c196_task.repositories.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepository courseRepository;
    private LiveData<List<Course>> courses;
    private LiveData<List<CourseWithAssessments>> courseDetails;
    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        courses = courseRepository.getCourses();
        courseDetails = courseRepository.getCourseDetails();
    }

    public LiveData<List<Course>> getCourses() { return courses; }

    public LiveData<List<CourseWithAssessments>> getCourseDetails() { return courseDetails; }

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
