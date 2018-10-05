package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.Result;

import java.util.List;

public interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWithId(Result result);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Result> results);

    @Update
    void update(Result result);

    @Update
    long updateWithId(Result result);

    @Update
    void updateMany(List<Result> results);

    @Delete
    void delete(Result result);

    @Delete
    long deleteWithId(Result result);

    @Delete
    void deleteMany(List<Result> results);

    @Query("SELECT * FROM results WHERE id = :resultId LIMIT 1")
    Result getResultById(long resultId);

    @Query("SELECT * FROM results WHERE test_id = :testId")
    List<Result> getResulstByTest(long testId);

    @Query("SELECT * FROM results WHERE test_id = :testId AND applicant_name LIKE :applicantName")
    List<Result> getResultByTestAndName(long testId, String applicantName);

    @Query("SELECT * FROM results WHERE test_id = :testId ORDER BY score ASC")
    List<Result> getResultByTestSorted(long testId);
}
