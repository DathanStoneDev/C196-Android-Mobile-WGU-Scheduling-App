package com.example.dathan_stone_c196_task.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dathan_stone_c196_task.entities.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTerm(Term term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTerms(List<Term> terms);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM terms")
    LiveData<List<Term>> findAllTerms();

    @Query("DELETE FROM terms")
    void deleteAllTerms();

}
