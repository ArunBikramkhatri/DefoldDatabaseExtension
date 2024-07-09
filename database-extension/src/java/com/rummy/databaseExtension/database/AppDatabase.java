package com.rummy.databaseExtension.database;

import com.rummy.databaseExtension.database.UserDao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { UserDao.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}