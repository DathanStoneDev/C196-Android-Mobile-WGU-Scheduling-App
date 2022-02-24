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

    public Term getTerm() {
        return term;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
