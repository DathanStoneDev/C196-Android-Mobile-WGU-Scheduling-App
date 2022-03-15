package com.example.dathan_stone_c196_task.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.DAO.CourseDAO;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;

import java.util.List;

public class CourseRepository {

    private CourseDAO courseDAO;
    private LiveData<List<Course>> courses;
    private LiveData<List<CourseWithAssessments>> courseDetails;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        courseDAO = db.courseDAO();
        courses = courseDAO.findAllCourses();
        courseDetails = courseDAO.findAllCourseDetails();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Course>> getCourses() { return courses; }

    public LiveData<List<CourseWithAssessments>> getCourseDetails() { return courseDetails; }

    public void addCourse(Course course) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            courseDAO.saveCourse(course);
        });
    }

    public void updateCourse(Course course) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            courseDAO.updateCourse(course);
        });
    }

    public void deleteCourse(Course course) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            courseDAO.deleteCourse(course);
        });
    }


}
