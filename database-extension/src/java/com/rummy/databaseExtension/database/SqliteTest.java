package com.rummy.databaseExtension.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rummy.databaseExtension.User;

import java.util.ArrayList;
import java.util.List;

public class SqliteTest extends SQLiteOpenHelper {
    private static final String TAG = "SqliteTest";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentlist";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public SqliteTest(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // 3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName()); // studentName

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        // 2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return studentList
        Log.d(TAG, "getStudent: " + user.toString());
        return user;
    }

    public void getUserList() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                // Adding student to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return student list
        Log.d(TAG, "getUserList: " + userList.toString());

    }
}
