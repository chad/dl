package com.chadfowler.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpener extends SQLiteOpenHelper {
    private DatabaseOpener(Context _c) {
        super(_c, "dl", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS reminders (id  INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, remindAt DATETIME, postedAt DATETIME");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
