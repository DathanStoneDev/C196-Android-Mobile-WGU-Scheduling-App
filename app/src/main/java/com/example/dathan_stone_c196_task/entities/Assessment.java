package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dathan_stone_c196_task.utilities.DateConverter;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "assessment_type")
    private String type;
    @ColumnInfo(name = "assessment_start_date")
    private Date startDate;
    @ColumnInfo(name = "assessment_end_date")
    private Date endDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
