package com.sergeyyaniuk.testity.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sergeyyaniuk.testity.data.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertWithId(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMany(List<User> users);

    @Update
    void update(User user);

    @Update
    int updateWithNumber(User user);

    @Update
    void updateMany(List<User> users);

    @Delete
    void delete(User user);

    @Delete
    int deleteWithNumber(User user);

    @Delete
    void deleteMany(List<User> users);

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    User getUserById(long userId);
}
