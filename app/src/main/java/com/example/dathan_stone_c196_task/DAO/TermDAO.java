package com.example.dathan_stone_c196_task.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.example.dathan_stone_c196_task.entities.Term;
import com.example.dathan_stone_c196_task.entities.TermWithCourses;

import java.util.List;

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

    @Query("SELECT * FROM terms")
    List<Term> findTermsNotLive();

    @Query("DELETE FROM terms")
    void deleteAllTerms();

    @Transaction
    @Query("SELECT * FROM terms WHERE term_id = :id")
    TermWithCourses getFullTermDetails(int id);



}
