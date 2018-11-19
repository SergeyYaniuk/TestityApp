package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.Test;

import java.util.List;

@Dao
public interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Test test);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<Test> tests);

    @Update
    void update(Test test);

    @Update
    void updateMany(List<Test> tests);

    @Delete
    void delete(Test test);

    @Delete
    void deleteMany(List<Test> tests);

    @Query("SELECT * FROM tests WHERE user_id = :userId")
    List<Test> getTestsByUserId(String userId);

    @Query("SELECT * FROM tests WHERE id = :testId LIMIT 1")
    Test getTestById(String testId);
}
