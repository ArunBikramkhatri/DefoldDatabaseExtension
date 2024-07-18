package com.rummy.databaseExtension;

import java.util.List;

import com.rummy.databaseExtension.database.SqliteTest;

import android.app.Activity;
import android.util.Log;

public class DatabaseExtension {

    private static final String TAG = "DatabaseExtension";

    private SqliteTest db;

    public DatabaseExtension(Activity activity) {
        this.activity = activity;
        Log.d(TAG, "DatabaseExtension: constructor called");
        db = new SqliteTest(activity);

    }

    public void insertData(String data, int id) {
        try {
            if (db  == null) {
                Log.d(TAG , "db is null")
            }
            Log.d(TAG, "insert data " + data);
            User user = new User(id , data);
            db.addUser(user);

            System.out.println("\n\n\\n");
            db.getUserList();

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        try {

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
            e.printStackTrace();
        }
    }
}
