package com.rummy.databaseExtension;

import java.util.List;

import javax.swing.Action;

import com.rummy.databaseExtension.database.AppDatabase;
import com.rummy.databaseExtension.database.UserDao;

import androidx.room.Room;
import android.app.Activity;

public class DatabaseExtension {

    private static final String TAG = "DatabaseExtension";
    private Activity activity;

    private AppDatabase db;
    private UserDao userDao;

    public DatabaseExtension(Activity activity) {
        this.activity = activity;
        Log.d(TAG, "DatabaseExtension: constructor called");
        setDatabase();
    }

    public void setDatabase() {
        try {
            db = Room.databaseBuilder(activity.getApplicationContext(),
                    AppDatabase.class, "testDb").build();

            userDao = db.getUserDao();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }

    }

    public void insertData(String data, int roll) {
        try {
            Log.d(TAG, "insert data " + data);
            User user = new User(data, roll);
            userDao.insertUser(user);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }

    }

    public void getAllUsers() {
        try {
            List<User> users = userDao.getAllUsers();
            Log.d(TAG, users.toString());
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

}
