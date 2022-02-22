package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.dathan_stone_c196_task.utilities.DateConverter;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "assessment_id")
    private int id;
    @ColumnInfo(name = "assessment_title")
    private String assessmentTitle;
    @ColumnInfo(name = "assessment_type")
    private String type;
    @ColumnInfo(name = "assessment_start_date")
    private Date startDate;
    @ColumnInfo(name = "course_id")
    private int courseId;

    public Assessment(String assessmentTitle, String type, Date startDate, int courseId) {
        this.assessmentTitle = assessmentTitle;
        this.type = type;
        this.startDate = startDate;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
