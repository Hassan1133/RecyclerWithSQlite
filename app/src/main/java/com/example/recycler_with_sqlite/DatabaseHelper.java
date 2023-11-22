package com.example.recycler_with_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "recycledb";
    public static final String TABLE_NAME = "student";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ID = "id";
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + " ( " + ID + " integer primary key autoincrement, " + NAME + " text, " + PHONE + " text unique )";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    Long insertData(String name, String phone) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        Long response = database.insert(TABLE_NAME, null, contentValues);
        return response;
    }

    Cursor fetchData() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }

    int updateData(int id, String name, String phone) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(PHONE, phone);
        int response = database.update(TABLE_NAME, contentValues, ID + " = " + id, null);
        return response;
    }
}
