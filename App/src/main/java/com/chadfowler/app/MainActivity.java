package com.chadfowler.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chadfowler.app.views.ReminderView;


public class MainActivity extends ActionBarActivity {
    public String oauthToken;
    private TextView taskLabel;
    private EditText taskField;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences("dl", MODE_PRIVATE);
        if (settings.contains(("oauthToken"))) {
            oauthToken = settings.getString("oauthToken", "");
            renderForm();


        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    private void renderForm() {
        setContentView(new ReminderView(this));
    }




}
