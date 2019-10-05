package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    //SignIn action
    public void gotoSuccessfulSignUpActivityAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SuccessfulSignUpActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
