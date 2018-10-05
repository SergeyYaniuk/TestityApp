package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.Question;

import java.util.List;

public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWithId(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Question> questions);

    @Update
    void update(Question question);

    @Update
    long updateWithId(Question question);

    @Update
    void updateMany(List<Question> questions);

    @Delete
    void delete(Question question);

    @Delete
    long deleteWithId(Question question);

    @Delete
    void deleteMany(List<Question> questions);

    @Query("SELECT * FROM questions WHERE id = :questionId LIMIT 1")
    Question getQuestionById(long questionId);

    @Query("SELECT * FROM questions WHERE test_id = :testId")
    List<Question> getQuestionsByTestId(long testId);

    @Query("SELECT * FROM questions WHERE test_id = :testId ORDER BY number ASC")
    List<Question> getQuestionsSorted(long testId);
}
