package com.example.dathan_stone_c196_task.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

@Entity
public class Term {

    @ColumnInfo(name="term_title")
    private String title;
    @ColumnInfo(name="term_start_date")
    private Date startDate;
    @ColumnInfo(name="term_end_date")
    private Date endDate;
}
