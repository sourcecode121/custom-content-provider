package com.example.contentprovider.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Created by Anand on 01/11/2016.
 */

public class UserDataDB extends UsersDB {

    public UserDataDB(Context context) {
        super(context);
    }

    public Cursor getUserData(String id, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder sqliteQueryBuilder = new SQLiteQueryBuilder();
        sqliteQueryBuilder.setTables(USERS_TABLE_NAME);
        if(id != null) {
            sqliteQueryBuilder.appendWhere(USERS_COLUMN_ID + " = " + id);
        }
        if(sortOrder == null || sortOrder == "") {
            sortOrder = USERS_COLUMN_ID;
        }
        Cursor cursor = sqliteQueryBuilder.query(getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        return cursor;
    }

    public long insertUserData(ContentValues values) throws SQLException {
        long id = getWritableDatabase().insert(USERS_TABLE_NAME, "", values);
        if(id <= 0) {
            throw new SQLException("Unable to add the user");
        }
        return id;
    }

    public int deleteUserData(String id) {
        if(id == null) {
            return getWritableDatabase().delete(USERS_TABLE_NAME, null , null);
        }
        else {
            return getWritableDatabase().delete(USERS_TABLE_NAME, USERS_COLUMN_ID + " = ?",
                    new String[]{ id });
        }
    }

    public int updateUserData(String id, ContentValues values) {
        if(id == null) {
            return getWritableDatabase().update(USERS_TABLE_NAME, values, null, null);
        }
        else {
            return getWritableDatabase().update(USERS_TABLE_NAME, values, USERS_COLUMN_ID + " = ?",
                    new String[]{ id });
        }
    }
}
