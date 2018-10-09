package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.Answer;

import java.util.List;

@Dao
public interface AnswerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Answer answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWithId(Answer answer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Answer> answers);

    @Update
    void update(Answer answer);

    @Update
    int updateWithNumber(Answer answer);

    @Update
    void updateMany(List<Answer> answers);

    @Delete
    void  delete(Answer answer);

    @Delete
    int deleteWithNumber(Answer answer);

    @Delete
    void deleteMany(List<Answer> answers);

    @Query("SELECT * FROM answers WHERE id = :answerId LIMIT 1")
    Answer getAnswerById(long answerId);

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    List<Answer> getAnswersByQuestionId(long questionId);

}
