package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "results",
        foreignKeys = @ForeignKey(entity = Test.class,
                parentColumns = "id", childColumns = "test_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = {@Index(value = "id", unique = true), @Index("test_id")})
public class Result {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private Long id;

    @ColumnInfo(name = "test_id")
    private String testId;

    @ColumnInfo(name = "applicant_name")
    private String applicantName;

    @ColumnInfo(name = "score")
    private int score;

    @Ignore
    public Result() {
    }

    public Result(String testId, String applicantName, int score) {
        this.testId = testId;
        this.applicantName = applicantName;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
