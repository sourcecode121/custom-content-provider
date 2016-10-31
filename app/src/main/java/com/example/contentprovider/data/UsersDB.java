package com.example.contentprovider.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.contentprovider.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 26/10/2016.
 */

public class UsersDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBUsers.db";
    protected static final String USERS_TABLE_NAME = "users";
    protected static final String USERS_COLUMN_ID = "id";
    private static final String USERS_COLUMN_FIRST_NAME = "first_name";
    private static final String USERS_COLUMN_LAST_NAME = "last_name";
    private static final String USERS_COLUMN_AGE = "age";

    private static final String USERS_TABLE_CREATE =
                    "CREATE TABLE IF NOT EXISTS " + USERS_TABLE_NAME + " (" +
                    USERS_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    USERS_COLUMN_FIRST_NAME + " TEXT, " +
                    USERS_COLUMN_LAST_NAME + " TEXT, " +
                    USERS_COLUMN_AGE + " INTEGER" +
                    ")";

    private static final String USERS_DROP_TABLE = "DROP TABLE IF EXISTS " + USERS_TABLE_NAME;

    private static final String USERS_SELECT_ALL = "SELECT * FROM " + USERS_TABLE_NAME;

    public UsersDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(USERS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(USERS_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUserDetails(String firstName, String lastName, int age) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_FIRST_NAME, firstName);
        contentValues.put(USERS_COLUMN_LAST_NAME, lastName);
        contentValues.put(USERS_COLUMN_AGE, age);
        if(sqLiteDatabase.insert(USERS_TABLE_NAME, null, contentValues) <= 0) {
            return false;
        }
        return true;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(USERS_SELECT_ALL, null);
        try {
            if (result.moveToFirst()) {
                while (!result.isAfterLast()) {
                    User user = new User();
                    user.setId(result.getInt(result.getColumnIndex(USERS_COLUMN_ID)));
                    user.setFirstName(result.getString(result.getColumnIndex(USERS_COLUMN_FIRST_NAME)));
                    user.setLastName(result.getString(result.getColumnIndex(USERS_COLUMN_LAST_NAME)));
                    user.setAge(result.getInt(result.getColumnIndex(USERS_COLUMN_AGE)));
                    users.add(user);
                    result.moveToNext();
                }
            }
        }
        finally {
            result.close();
        }
        return users;
    }

    public boolean updateUserDetails(int id, String firstName, String lastName, int age) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_FIRST_NAME, firstName);
        contentValues.put(USERS_COLUMN_LAST_NAME, lastName);
        contentValues.put(USERS_COLUMN_AGE, age);
        if(sqLiteDatabase.update(USERS_TABLE_NAME, contentValues, "id = ? ",
                                new String[]{ String.valueOf(id) }) == 0) {
            return false;
        }
        return true;
    }

    public boolean deleteUserDetails(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if(sqLiteDatabase.delete(USERS_TABLE_NAME, USERS_COLUMN_ID + " = ? ",
                                new String[]{ String.valueOf(id) }) == 0) {
            return false;
        }
        return true;
    }
}
