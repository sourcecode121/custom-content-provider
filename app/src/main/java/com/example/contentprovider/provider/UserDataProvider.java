package com.example.contentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.contentprovider.data.UserDataDB;
import com.example.contentprovider.data.UsersDB;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;

/**
 * Created by Anand on 31/10/2016.
 */

public class UserDataProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.example.contentprovider.provider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/users");
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(PROVIDER_NAME, "users", 1);
        uriMatcher.addURI(PROVIDER_NAME, "users/#", 2);
    }
    private UserDataDB userDataDB;

    @Override
    public boolean onCreate() {
        userDataDB = new UserDataDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        if(uriMatcher.match(uri) == 2) {
            id = uri.getPathSegments().get(1);
        }
        return userDataDB.getUserData(id, projection, selection, selectionArgs, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME + ".users";
            case 2:
                return "vnd.android.cursor.item/vnd." + PROVIDER_NAME + ".users";
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        try {
            long id = userDataDB.insertUserData(contentValues);
            return ContentUris.withAppendedId(CONTENT_URI, id);
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == 2) {
            id = uri.getPathSegments().get(1);
        }
        return userDataDB.deleteUserData(id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == 2) {
            id = uri.getPathSegments().get(1);
        }
        return userDataDB.updateUserData(id, contentValues);
    }
}
