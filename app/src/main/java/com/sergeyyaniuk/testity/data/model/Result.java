package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "results",
        foreignKeys = @ForeignKey(entity = Test.class,
                parentColumns = "id", childColumns = "test_id", onDelete = CASCADE, onUpdate = CASCADE),
        indices = {@Index(value = "id", unique = true), @Index("test_id")})
public class Result {

    @PrimaryKey()
    @NonNull
    private String id;

    @ColumnInfo(name = "test_id")
    private String testId;

    @ColumnInfo(name = "applicant_name")
    private String applicantName;

    @ColumnInfo(name = "score")
    private double score;

    @ColumnInfo(name = "user_id")
    private String userId;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "date")
    private String date;

    @Ignore
    public Result() {
    }

    public Result(@NonNull String id, String testId, String applicantName, double score, String userId,
                  String userName, String date) {
        this.id = id;
        this.testId = testId;
        this.applicantName = applicantName;
        this.score = score;
        this.userId = userId;
        this.userName = userName;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
