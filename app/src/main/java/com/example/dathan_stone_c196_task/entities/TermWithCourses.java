package com.example.dathan_stone_c196_task.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TermWithCourses {
    @Embedded public Term term;
    @Relation(
            parentColumn = "term_id",
            entityColumn = "term_id"
    )
    public List<Course> courses;
}
