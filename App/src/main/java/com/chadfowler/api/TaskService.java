package com.chadfowler.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.data.DatabaseOpener;
import com.chadfowler.data.Task;

import java.util.Date;
import java.util.List;

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
            postInBackground();
            return tasks[0];
        }

        public boolean addWithReminder(String title, Date reminder) {
            return saveLocally(new Task(title, reminder)) && postInBackground();
        }

        private boolean postInBackground() {
            Log.d("genau", "posting in background");
            DatabaseOpener opener = new DatabaseOpener(caller.getApplicationContext());
            List<Task> tasks = opener.getAllThatNeedToBePosted();
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                post(t);
                opener.setPosted(t);
            }

            return false;
        }

        private void post(Task t) {
            postTask(t);
            postReminder(t);
        }

        private void postReminder(Task t) {
            //FIXME

        }

        private void postTask(Task t) {
            //FIXME

        }


        private boolean saveLocally(Task t) {
            Log.d("genau", "saving locally");
            DatabaseOpener opener = new DatabaseOpener(caller.getApplicationContext());
            return opener.insert(t);
        }
    }

}
