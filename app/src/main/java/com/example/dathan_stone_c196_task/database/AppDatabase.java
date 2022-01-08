package com.example.dathan_stone_c196_task.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dathan_stone_c196_task.DAO.TermDAO;
import com.example.dathan_stone_c196_task.entities.Assessment;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Instructor;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.utilities.DateConverter;

/**
 * Declares the Database and adds the Course, Instructor, Term and Assessment entities to the database.
 */
@Database(entities = {Course.class, Instructor.class, Term.class, Assessment.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "data.db";
    private static AppDatabase appDatabase;

    //Singleton that provides a single DB connection instance.
    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }
        return appDatabase;
    }
    public abstract TermDAO termDAO();
}
