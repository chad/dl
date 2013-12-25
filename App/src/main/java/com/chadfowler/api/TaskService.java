package com.chadfowler.api;

import android.util.Log;

import java.util.Date;

public class TaskService implements ITaskService {
    public boolean addWithReminder(String title, Date reminder) {
        return saveLocally(title, reminder) && postInBackground();
    }

    private static boolean postInBackground() {
        Log.d("genau", "posting in background");
        // However it works on Android, start a background worker that posts any
        // tasks which have not yet been posted and then set them to posted.
        return false;
    }

    private static boolean saveLocally(String title, Date reminder) {
        Log.d("genau", "saving locally");
        // Save to SQLite locally.  Include a flag that says whether the data has been posted (maybe a date/time?). Set to false.
        return false;
    }
}
