package com.chadfowler.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chadfowler.api.TaskService;

import java.util.Date;


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
        LinearLayout main = new LinearLayout(this);
        main.setLayoutParams(new android.app.ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        main.setOrientation(LinearLayout.VERTICAL);

        taskLabel = new TextView(this);
        taskLabel.setText("Remind me about:");
        taskField = new EditText(this);
        button = new Button(this);
        button.setText("Remind me");
        button.setOnClickListener(makeClick());
        main.addView(taskLabel);
        main.addView(taskField);
        main.addView(button);
        setContentView(main);
    }

    private View.OnClickListener makeClick() {
        final Activity caller = this;

        return new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences("dl", MODE_PRIVATE);
                int userId = settings.getInt("userId", 0);
                //FIXME userId is required
                new TaskService(caller).addWithReminder(userId, taskField.getText().toString(), new Date());
            }
        };
    }


}
