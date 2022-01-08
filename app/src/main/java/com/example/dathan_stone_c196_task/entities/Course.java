package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dathan_stone_c196_task.utilities.DateConverter;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "course_title")
    private String title;
    @ColumnInfo(name = "course_start_date")
    private Date startDate;
    @ColumnInfo(name = "course_end_date")
    private Date endDate;
    //Possibly make enum
    @ColumnInfo(name = "course_status")
    private String status;
    @ColumnInfo(name = "course_note")
    private String note;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    //courses can have multiple assessments
}
