package com.rummy.databaseExtension.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.rummy.databaseExtension.User;

@Database(entities = { User.class }, version = 1)
public abstract class RummyDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
