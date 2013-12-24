package com.chadfowler.api;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.chadfowler.data.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginService implements ILoginService {
    private final Activity caller;

    public LoginService(Activity caller) {
        this.caller = caller;
    }

    public void login(String email, String password) throws User.UserConstructionException {
        new LoginTask(caller).execute();
    }


    private class LoginTask extends AsyncTask<String, Integer, Integer> {
        private final Activity caller;
        String json = null;

        public LoginTask(Activity caller) {
            this.caller = caller;
        }

        @Override
        protected Integer doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            try {
                json = makeHttpRequest(email, password);


            } catch (IOException e) {
                e.printStackTrace(); //FIXME

            }
            return 1; //FIXME
        }

        @Override
        public void onProgressUpdate(Integer... i) {
            Log.d("genau", "Progress: " + i);
        }

        @Override
        protected void onPostExecute(Integer i) {
            caller.finish();
        }

        private String processHttpResponse(HttpResponse httpResponse) throws IOException {
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "n");
                }
                is.close();
                return sb.toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }

        }


        private String makeHttpRequest(String email, String password) throws IOException {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("client_id", "802ddb140a9ee060de8c")); // DL
            HttpPost httpPost = new HttpPost("http://a.wunderlist.com/api/v1/user/authenticate");

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);

            return processHttpResponse(httpResponse);
        }


    }
}
