package com.chadfowler.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseOpener extends SQLiteOpenHelper {
    private final SimpleDateFormat dateFormat;

    public DatabaseOpener(Context _c) {
        super(_c, "dl", null, 1);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public List<Task> getAllThatNeedToBePosted() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from reminders where postedAt IS NULL", null);
        List<Task> list = new ArrayList<Task>();
        int i = 0;
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                list.add(new Task(c.getString(c.getColumnIndex("title")), stringAsDate(c.getString(c.getColumnIndex("remindAt")))));
            } while (c.moveToNext());
            c.close();
        }
        return list;

    }

    private Date stringAsDate(String str) {
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            Log.e("dl", e.getMessage());
            return null; // GOD I HATE THESE EXCEPTIONS
        }
    }
}
