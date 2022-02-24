package com.example.dathan_stone_c196_task.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dathan_stone_c196_task.DAO.CourseDAO;
import com.example.dathan_stone_c196_task.database.AppDatabase;
import com.example.dathan_stone_c196_task.entities.Course;

import java.util.List;

public class CourseRepository {

    private CourseDAO courseDAO;
    private LiveData<List<Course>> courses;
    private LiveData<List<Course>> termCourses;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        courseDAO = db.courseDAO();
        courses = courseDAO.findAllCourses();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LiveData<List<Course>> getCourses() { return courses; }

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

    public LiveData<List<Course>> findTermCourses(int id) {
        AppDatabase.databaseWriteExecutor.execute(()-> {
            termCourses = courseDAO.findTermCourses(id);
        });
        return termCourses;
    }
}
