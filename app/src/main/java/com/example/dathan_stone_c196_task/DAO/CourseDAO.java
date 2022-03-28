package com.example.dathan_stone_c196_task.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.CourseWithAssessments;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCourse(Course course);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCourses(List<Course> courses);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM courses")
    LiveData<List<Course>> findAllCourses();

    @Transaction
    @Query("SELECT * FROM courses")
    LiveData<List<CourseWithAssessments>> findAllCourseDetails();

}
