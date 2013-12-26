package com.chadfowler.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.data.DatabaseOpener;
import com.chadfowler.data.Task;

import java.util.Date;

public class TaskService implements ITaskService {
    private final Activity caller;

    public TaskService(Activity caller) {
        this.caller = caller;
    }


    public void addWithReminder(String title, Date reminder) {
        new TaskCreationTask(caller).execute(new Task(title, reminder));
    }

    private class TaskCreationTask extends AsyncTask<Task, Task, Task> {
        private final Activity caller;

        public TaskCreationTask(Activity caller) {
            this.caller = caller;
        }

        @Override
        protected Task doInBackground(Task... tasks) {
            saveLocally(tasks[0]);
            return tasks[0];
        }

        public boolean addWithReminder(String title, Date reminder) {
            return saveLocally(new Task(title, reminder)) && postInBackground();
        }

        private boolean postInBackground() {
            Log.d("genau", "posting in background");
            // However it works on Android, start a background worker that posts any
            // tasks which have not yet been posted and then set them to posted.
            return false;
        }

        private boolean saveLocally(Task t) {
            Log.d("genau", "saving locally");
            DatabaseOpener opener = new DatabaseOpener(caller.getApplicationContext());
            return opener.insert(t);
        }
    }

}
