package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "answers",
        foreignKeys = @ForeignKey(entity = Question.class,
                parentColumns = "id", childColumns = "question_id", onDelete = CASCADE, onUpdate = CASCADE))
public class Answer {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "answer_text")
    private String answerText;

    @ColumnInfo(name = "is_correct")
    private boolean isCorrect;

    @ColumnInfo(name = "question_id")
    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
