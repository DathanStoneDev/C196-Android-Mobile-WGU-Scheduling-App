package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "courses")
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "course_id")
    private int courseId;
    @ColumnInfo(name = "course_title")
    private String title;
    @ColumnInfo(name = "course_start_date")
    private Date startDate;
    @ColumnInfo(name = "course_end_date")
    private Date endDate;
    @ColumnInfo(name = "course_status")
    private String status;
    @ColumnInfo(name = "course_note")
    private String note;
    @ColumnInfo(name = "term_id")
    private int termId;
    @ColumnInfo(name = "instructor_name")
    private String instructorName;
    @ColumnInfo(name = "instructor_phone_number")
    private String instructorPhoneNumber;
    @ColumnInfo(name = "instructor_email")
    private String instructorEmail;
    @ColumnInfo
    private int startCourseAlarmId;
    @ColumnInfo
    private int endCourseAlarmId;

    public Course(String title, Date startDate, Date endDate, String status, String note, int termId, String instructorName, String instructorPhoneNumber, String instructorEmail, int startCourseAlarmId, int endCourseAlarmId) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.termId = termId;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
        this.startCourseAlarmId = startCourseAlarmId;
        this.endCourseAlarmId = endCourseAlarmId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getStartCourseAlarmId() {
        return startCourseAlarmId;
    }

    public void setStartCourseAlarmId(int startCourseAlarmId) {
        this.startCourseAlarmId = startCourseAlarmId;
    }

    public int getEndCourseAlarmId() {
        return endCourseAlarmId;
    }

    public void setEndCourseAlarmId(int endCourseAlarmId) {
        this.endCourseAlarmId = endCourseAlarmId;
    }

    @Override
    public String toString() {
        return title;
    }
}
