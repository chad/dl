package com.chadfowler.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
    private String oauthToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = getSharedPreferences("dl", MODE_PRIVATE);
        if (settings.contains(("oauthToken"))) {
            oauthToken = settings.getString("oauthToken", "");

        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
