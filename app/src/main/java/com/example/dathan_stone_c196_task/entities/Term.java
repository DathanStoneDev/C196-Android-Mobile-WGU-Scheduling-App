package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dathan_stone_c196_task.utilities.DateConverter;

import java.util.Date;
import java.util.List;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="term_title")
    private String title;
    @ColumnInfo(name="term_start_date")
    private Date startDate;
    @ColumnInfo(name="term_end_date")
    private Date endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    //terms can have a list of courses
}
