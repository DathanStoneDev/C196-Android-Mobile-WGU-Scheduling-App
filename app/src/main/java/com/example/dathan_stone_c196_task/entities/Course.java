package com.example.dathan_stone_c196_task.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity
public class Course {

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
}
