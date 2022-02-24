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
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;

import java.util.List;
import java.util.Map;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTerm(Term term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM terms")
    LiveData<List<Term>> findAllTerms();

    @Transaction
    @Query("SELECT * FROM terms")
    LiveData<List<TermWithCourses>> findAllTermDetails();



}
