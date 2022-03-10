package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "term_id")
    private int id;
    @ColumnInfo(name="term_title")
    private String title;
    @ColumnInfo(name="term_start_date")
    private Date startDate;
    @ColumnInfo(name="term_end_date")
    private Date endDate;

    public Term(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return title;
    }
}
