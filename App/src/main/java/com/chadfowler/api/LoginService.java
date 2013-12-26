package com.chadfowler.api;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.data.User;

public class LoginService implements ILoginService {
    private final Activity caller;

    public LoginService(Activity caller) {
        this.caller = caller;
    }

    public void login(String email, String password) {
        new LoginTask(caller).execute(email, password);
    }


    private class LoginTask extends AsyncTask<String, User, User> {
        private final Activity caller;
        String json = null;

        public LoginTask(Activity caller) {
            this.caller = caller;
        }

        @Override
        protected User doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            try {
                Posticle posticle = new Posticle("/user/authenticate");
                posticle.addParam("email", email);
                posticle.addParam("password", password);
                json = posticle.makeHttpRequest();
                User u = User.fromJSON(json);
                return u;

            } catch (Exception e) {
                e.printStackTrace(); //FIXME
            }
            return null; //FIXME
        }

        @Override
        public void onProgressUpdate(User... u) {
            Log.d("genau", "Progress: " + u);
        }

        @Override
        protected void onPostExecute(User u) {
            saveToken(u.accessToken);
            caller.finish();
        }

        private void saveToken(String accessToken) {
            SharedPreferences settings = caller.getSharedPreferences("dl", caller.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("oauthToken", accessToken);
            editor.commit();
        }




    }
}
