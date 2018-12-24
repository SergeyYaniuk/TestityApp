package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.Question;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Question> questions);

    @Update
    void update(Question question);

    @Update
    void updateMany(List<Question> questions);

    @Delete
    void delete(Question question);

    @Delete
    void deleteMany(List<Question> questions);

    @Query("SELECT * FROM questions WHERE id = :questionId LIMIT 1")
    Question getQuestionById(String questionId);

    @Query("SELECT * FROM questions WHERE test_id = :testId")
    List<Question> getQuestionsByTestId(String testId);

    @Query("DELETE FROM questions WHERE id = :questionId")
    void deleteQuestionById(String questionId);
}
