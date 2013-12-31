package com.chadfowler.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.app.MainActivity;
import com.chadfowler.data.DatabaseOpener;
import com.chadfowler.data.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class TaskService {
    private final MainActivity caller;

    public TaskService(Activity caller) {
        this.caller = (MainActivity) caller;
    }


    public void addWithReminder(int userId, String title, Date reminder) {
        int inboxId = -userId;
        new TaskCreationTask(caller).execute(new Task(title, reminder, inboxId));
    }

    private class TaskCreationTask extends AsyncTask<Task, Task, Task> {
        private final MainActivity caller;

        public TaskCreationTask(Activity caller) {
            this.caller = (MainActivity) caller;
        }

        @Override
        protected Task doInBackground(Task... tasks) {
            saveLocally(tasks[0]);
            postInBackground();
            return tasks[0];
        }

        @Override
        public void onPostExecute(Task t) {
            caller.reset();
        }

        private boolean postInBackground() {
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
            Posticle posticle = new Posticle("/reminders", caller.oauthToken);
            posticle.addParam("date", new Date().toString()); // Date format
            posticle.addParam("task_id", t.id);
            String json = posticle.makeHttpRequest();
            Log.d("genau", "reminder json:" + json);
        }

        private String postTask(Task t) throws IOException, JSONException {
            Posticle posticle = new Posticle("/tasks", caller.oauthToken);
            posticle.addParam("title", t.title); // Date format
            posticle.addParam("list_id", Integer.toString(t.listId));

            String json = posticle.makeHttpRequest();
            JSONObject taskData = new JSONObject(json);
            return taskData.getString("id");
        }


        private boolean saveLocally(Task t) {
            DatabaseOpener opener = new DatabaseOpener(caller.getApplicationContext());
            return opener.insert(t);
        }
    }

}
