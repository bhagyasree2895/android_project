package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PostingAvailabilityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_availability);
    }
    public void gotohomePage(View v) {
        try {
            Intent toOtherIntent = new Intent(this,SignInActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotouploadimage(View v) {
        try {
            Intent toOtherIntent = new Intent(this,uploadImageActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }

    }
    public void onSubmit(View v) {
        try {
            Intent toOtherIntent = new Intent(this,submitActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }

    }
}
