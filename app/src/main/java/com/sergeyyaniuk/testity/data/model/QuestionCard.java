package com.sergeyyaniuk.testity.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class QuestionCard {

    @Embedded
    public Question question;

    @Relation(parentColumn = "id", entityColumn = "question_id")
    public List<Answer> answers;
}
