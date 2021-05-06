package com.my.elenabackup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;


public class MyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ELENA";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_NAME = "picture_path";
    public static final String TABLE_NAME = "pictures";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME + " TEXT)";

    public MyDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addPicturePath(List<String> pathsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < pathsList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, pathsList.get(i));
            db.insert(TABLE_NAME, COLUMN_NAME, contentValues);
        }
    }
}
