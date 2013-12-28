package com.chadfowler.app;

import android.app.Activity;
import android.os.Bundle;

import com.chadfowler.app.views.LoginView;

/**
 * Created by chad on 22/12/13.
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginView main = new LoginView(this);

        setContentView(main);
    }


}
