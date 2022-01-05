package com.example.dathan_stone_c196_task.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Instructor {

    @ColumnInfo(name = "instructor_name")
    private String name;
    @ColumnInfo(name = "instructor_phone_number")
    private String phoneNumber;
    @ColumnInfo(name = "instructor_email")
    private String email;
}
