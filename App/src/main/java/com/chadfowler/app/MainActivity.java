package com.chadfowler.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.chadfowler.app.views.ReminderView;


public class MainActivity extends ActionBarActivity {
    public String oauthToken;
    private static int LOGIN_REQUEST_CODE = 1;
    private ReminderView reminderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!setOauthTokenFromSettings()) {
            startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), LOGIN_REQUEST_CODE);
        }
        this.reminderView = new ReminderView(this);
        setContentView(reminderView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("oauthToken")) {
                this.oauthToken = data.getStringExtra("oauthToken");
            }
        }
    }

    public void reset() {
        Toast.makeText(this, "The reminder was created", Toast.LENGTH_SHORT).show();
        reminderView.reset();

    }

    private boolean setOauthTokenFromSettings() {
        SharedPreferences settings = getSharedPreferences("dl", MODE_PRIVATE);
        if (settings.contains("oauthToken")) {
            oauthToken = settings.getString("oauthToken", "");
            return true;
        } else {
            return false;
        }
    }

}
