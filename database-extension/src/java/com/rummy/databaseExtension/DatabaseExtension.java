package com.rummy.databaseExtension;

import java.util.List;
import android.app.Activity;
import android.util.Log;
import androidx.room.Room;
import com.rummy.databaseExtension.database.RummyDatabase;
import com.rummy.databaseExtension.database.UserDao;

public class DatabaseExtension {

    private static final String TAG = "DatabaseExtension";
    private Activity activity;

    private RummyDatabase db;
    private UserDao userDao;

    public DatabaseExtension(Activity activity) {
        this.activity = activity;
        Log.d(TAG, "DatabaseExtension: constructor called");
        setDatabase();
    }

    public void setDatabase() {
        try {
            db = Room.databaseBuilder(activity.getApplicationContext(),
                    RummyDatabase.class, "testDb").build();
            userDao = db.getUserDao();
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

    public void insertData(String data, int roll) {
        try {
            if (userDao == null) {
                setDatabase();
            }
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
            if (userDao == null) {
                setDatabase();
            }
            List<User> users = userDao.getAllUsers();
            Log.d(TAG, users.toString());
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }
}
