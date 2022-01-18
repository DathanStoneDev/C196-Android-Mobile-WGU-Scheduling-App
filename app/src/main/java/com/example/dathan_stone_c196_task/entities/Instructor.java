package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "instructor_id")
    private int id;
    @ColumnInfo(name = "instructor_name")
    private String name;
    @ColumnInfo(name="course_id")
    private String courseId;
    @ColumnInfo(name = "instructor_phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "instructor_email")
    private String email;

    public Instructor(String name, String courseId, String phoneNumber, String email) {
        this.name = name;
        this.courseId = courseId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
