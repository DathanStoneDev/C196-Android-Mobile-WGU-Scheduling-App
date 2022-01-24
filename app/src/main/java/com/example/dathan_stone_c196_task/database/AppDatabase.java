package com.example.dathan_stone_c196_task.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dathan_stone_c196_task.DAO.AssessmentDAO;
import com.example.dathan_stone_c196_task.DAO.CourseDAO;
import com.example.dathan_stone_c196_task.DAO.TermDAO;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Instructor;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.utilities.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Declares the Database and adds the Course, Instructor, Term and Assessment entities to the database.
 */
@Database(entities = {Course.class, Instructor.class, Term.class, Assessment.class}, version = 9)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    //DAOs that will be exposed via abstract getter methods
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static final String DATABASE_NAME = "data.db";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 3;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Singleton that provides a single DB connection instance.
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
