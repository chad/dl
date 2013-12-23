package com.chadfowler.api;

import java.util.Date;

public class TaskService implements ITaskService {
    public boolean addWithReminder(String title, Date reminder) {
        return saveLocally(title, reminder) && postInBackground();
    }

    private static boolean postInBackground() {
        // However it works on Android, start a background worker that posts any
        // tasks which have not yet been posted and then set them to posted.
        return false;
    }

    private static boolean saveLocally(String title, Date reminder) {

        // Save to SQLite locally.  Include a flag that says whether the data has been posted (maybe a date/time?). Set to false.
        return false;
    }
}
