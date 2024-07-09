package com.rummy.databaseExtension.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAllUsers();

    @Query
    void insertUser(User user);
}