package com.chadfowler.app.views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chadfowler.api.TaskService;

import java.util.Date;

public class ReminderView extends LinearLayout {
    private final Activity container;
    private TextView taskLabel;
    private EditText taskField;
    private Button button;

    public ReminderView(Activity container) {
        super(container);
        this.container = container;
        setupLayout();
    }

    public void reset() {
        taskField.setText("");
        taskField.setFocusableInTouchMode(true);
        taskField.requestFocus();
    }

    private void setupLayout() {
        setLayoutParams(new android.app.ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        taskLabel = new TextView(container);
        taskLabel.setText("Remind me about:");
        taskField = new EditText(container);
        button = new Button(container);
        button.setText("Remind me");
        button.setOnClickListener(makeClick());
        addView(taskLabel);
        addView(taskField);
        addView(button);
    }

    private View.OnClickListener makeClick() {
        final Activity caller = container;

        return new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = container.getSharedPreferences("dl", Context.MODE_PRIVATE);
                int userId = settings.getInt("userId", 0);
                //FIXME userId is required
                new TaskService(caller).addWithReminder(userId, taskField.getText().toString(), new Date());
            }
        };
    }

}
