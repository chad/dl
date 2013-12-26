package com.chadfowler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseOpener extends SQLiteOpenHelper {
    public DatabaseOpener(Context _c) {
        super(_c, "dl", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS reminders (id  INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, remindAt DATETIME, postedAt DATETIME");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Intentionally left blank
    }

    public boolean insert(Task t) {
        ContentValues values = new ContentValues();
        values.put("title", t.title);
        values.put("remindAt", dateAsString(t.remindAt));
        SQLiteDatabase db = getWritableDatabase();
        db.insert("reminders", null, values);
        return true;
    }

    public void setPosted(Task t) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("postedAt", dateAsString(new Date()));
        db.update("reminders", values, "title = '?'", new String[]{t.title});
    }

    private String dateAsString(java.util.Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(d);
    }
}
