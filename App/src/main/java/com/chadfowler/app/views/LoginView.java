package com.chadfowler.app.views;

import android.app.Activity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chadfowler.api.LoginService;

public class LoginView extends LinearLayout {
    private final Activity container;
    public TextView emailLabel;
    public TextView passwordLabel;
    public Button button;
    public EditText emailField;
    public EditText passwordField;


    public LoginView(Activity container) {
        super(container);
        this.container = container;
        setupLayout();
    }

    private void setupLayout() {
        setLayoutParams(new android.app.ActionBar.LayoutParams(android.app.ActionBar.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);


        setupLoginForm();
    }

    private void setupLoginForm() {
        LinearLayout emailSection = new LinearLayout(this.container);
        emailSection.setOrientation(LinearLayout.HORIZONTAL);
        setupEmailSection(emailSection);
        LinearLayout passwordSection = new LinearLayout(this.container);
        setupPasswordSection(passwordSection);
        setupLoginButton();
        addChildViews(emailSection, passwordSection);
    }

    private void addChildViews(LinearLayout emailSection, LinearLayout passwordSection) {
        addView(emailSection);
        addView(passwordSection);
        addView(button);
    }

    private void setupLoginButton() {
        button = new Button(this.container);
        button.setText("Login");
        button.setOnClickListener(makeClick());
    }

    private void setupPasswordSection(LinearLayout passwordSection) {
        passwordSection.setOrientation(LinearLayout.HORIZONTAL);
        passwordLabel = new TextView(this.container);
        passwordLabel.setText("Password:");
        passwordField = new EditText(this.container);
        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordSection.addView(passwordLabel);
        passwordSection.addView(passwordField);
    }

    private void setupEmailSection(LinearLayout userSection) {
        emailLabel = new TextView(this.container);
        emailLabel.setText("Email:");
        emailField = new EditText(this.container);
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        userSection.addView(emailLabel);
        userSection.addView(emailField);
    }

    protected View.OnClickListener makeClick() {
        final Activity caller = this.container;
        return new View.OnClickListener() {
            public void onClick(View v) {
                new LoginService(caller).login(emailField.getText().toString(), passwordField.getText().toString());

            }
        };

    }
}
