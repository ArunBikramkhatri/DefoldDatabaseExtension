package com.rummy.databaseExtension.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import com.rummy.databaseExtension.User;

@Dao
public interface UserDao {

    @Query("select * from User")
    List<User> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}