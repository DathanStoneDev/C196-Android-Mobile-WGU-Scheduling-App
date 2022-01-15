package com.example.dathan_stone_c196_task.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "instructor_name")
    private String name;
    @ColumnInfo(name="course")
    private String associatedCourse;
    @ColumnInfo(name = "instructor_phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "instructor_email")
    private String email;

    public Instructor(@NonNull String name, String associatedCourse, String phoneNumber, String email) {
        this.name = name;
        this.associatedCourse = associatedCourse;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAssociatedCourse() {
        return associatedCourse;
    }
}
