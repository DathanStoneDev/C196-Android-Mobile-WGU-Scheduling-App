package com.example.dathan_stone_c196_task.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dathan_stone_c196_task.DAO.TermDAO;
import com.example.dathan_stone_c196_task.entities.Course;
import com.example.dathan_stone_c196_task.entities.Instructor;
import com.example.dathan_stone_c196_task.entities.Term;

@Database(entities = {Course.class, Instructor.class, Term.class, }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
}
