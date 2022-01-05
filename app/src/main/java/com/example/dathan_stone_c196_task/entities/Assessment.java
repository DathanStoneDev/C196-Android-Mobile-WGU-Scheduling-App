package com.example.dathan_stone_c196_task.entities;

import androidx.room.Entity;

import java.util.Date;

@Entity
public class Assessment {
    //Possibly make as enum
    private String type;
    private Date startDate;
    private Date endDate;
}
