package com.example.dathan_stone_c196_task.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CourseWithAssessments {
    @Embedded public Course course;
    @Relation(
            parentColumn = "course_id",
            entityColumn = "course_id"
    )
    public List<Assessment> assessments;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }
}
