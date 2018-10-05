package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "questions",
        foreignKeys = @ForeignKey(entity = Test.class,
                parentColumns = "id", childColumns = "test_id", onDelete = CASCADE, onUpdate = CASCADE))
public class Question {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "question_text")
    private String questionText;

    @ColumnInfo(name = "number")
    private int number;

    @ColumnInfo(name = "test_id")
    private Long testId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}
