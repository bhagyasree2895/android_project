package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //SignIn action
    public void gotoSignInAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SignInActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
