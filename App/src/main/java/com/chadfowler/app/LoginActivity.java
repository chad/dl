package com.chadfowler.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chadfowler.api.LoginService;
import com.chadfowler.data.User;

/**
 * Created by chad on 22/12/13.
 */
public class LoginActivity extends Activity {
    public TextView emailLabel;
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
        LinearLayout emailSection = new LinearLayout(this);
        emailSection.setOrientation(LinearLayout.HORIZONTAL);
        setupEmailSection(emailSection);
        LinearLayout passwordSection = new LinearLayout(this);
        setupPasswordSection(passwordSection);
        setupLoginButton();
        addChildViews(main, emailSection, passwordSection);
    }

    private void addChildViews(LinearLayout main, LinearLayout emailSection, LinearLayout passwordSection) {
        main.addView(emailSection);
        main.addView(passwordSection);
        main.addView(button);
    }

    private void setupLoginButton() {
        button = new Button(this);
        button.setText("Login");
        button.setOnClickListener(makeClick());
    }

    private void setupPasswordSection(LinearLayout passwordSection) {
        passwordSection.setOrientation(LinearLayout.HORIZONTAL);
        passwordLabel = new TextView(this);
        passwordLabel.setText("Password:");
        passwordField = new EditText(this);
        passwordField.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordSection.addView(passwordLabel);
        passwordSection.addView(passwordField);
    }

    private void setupEmailSection(LinearLayout userSection) {
        emailLabel = new TextView(this);
        emailLabel.setText("Email:");
        emailField = new EditText(this);
        emailField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        userSection.addView(emailLabel);
        userSection.addView(emailField);
    }

    protected View.OnClickListener makeClick() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                try {
                    User u = new LoginService().login(emailField.getText().toString(), passwordField.getText().toString());
                    saveToken(u.accessToken);
                    builder.setMessage("email: " + emailField.getText() + ", password: " + passwordField.getText())
                            .setTitle("a dialog");
                    finish();
                } catch (User.UserConstructionException e) {
                    builder.setMessage(e.getMessage())
                            .setTitle("a dialog");

                }


                builder.show();

            }
        };

    }

    private void saveToken(String accessToken) {
        SharedPreferences settings = getSharedPreferences("dl", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("oauthToken", accessToken);
        editor.commit();
    }
}
