package com.chadfowler.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public TextView userLabel;
    public TextView passwordLabel;
    public Button button;
    public EditText emailField;
    public EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout main = new LinearLayout(this);
        main.setLayoutParams(new android.app.ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        main.setOrientation(LinearLayout.VERTICAL);


        setupLoginForm(main);

        setContentView(main);
    }

    private void setupLoginForm(LinearLayout main) {
        LinearLayout userSection = new LinearLayout(this);
        userSection.setOrientation(LinearLayout.HORIZONTAL);


        userLabel = new TextView(this);
        userLabel.setText("Email:");
        emailField = new EditText(this);
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        userSection.addView(userLabel);
        userSection.addView(emailField);

        LinearLayout passwordSection = new LinearLayout(this);
        userSection.setOrientation(LinearLayout.HORIZONTAL);
        passwordLabel = new TextView(this);
        passwordLabel.setText("Password:");
        passwordField = new EditText(this);
        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordSection.addView(passwordLabel);
        passwordSection.addView(passwordField);

        button = new Button(this);
        button.setText("Login");
        button.setOnClickListener(makeClick());
        main.addView(userSection);
        main.addView(passwordSection);
        main.addView(button);
    }

    protected View.OnClickListener makeClick() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(v.getContext());

                builder.setMessage("email: " + emailField.getText() + ", password: " + passwordField.getText())
                        .setTitle("a dialog");
                builder.show();
            }
        };

    }
}
