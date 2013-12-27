package com.chadfowler.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.data.DatabaseOpener;
import com.chadfowler.data.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
            try {
                postTask(t);
                postReminder(t);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void postReminder(Task t) throws IOException {
            Posticle posticle = new Posticle("/reminders");
            posticle.addParam("date", new Date().toString()); // Date format
            posticle.addParam("task_id", t.id);
            String json = posticle.makeHttpRequest();
        }

        private String postTask(Task t) throws IOException, JSONException {
            Posticle posticle = new Posticle("/tasks");
            posticle.addParam("title", t.title); // Date format
            String json = posticle.makeHttpRequest();
            JSONObject taskData = new JSONObject(json);
            return taskData.getString("id");
        }


        private boolean saveLocally(Task t) {
            Log.d("genau", "saving locally");
            DatabaseOpener opener = new DatabaseOpener(caller.getApplicationContext());
            return opener.insert(t);
        }
    }

}
