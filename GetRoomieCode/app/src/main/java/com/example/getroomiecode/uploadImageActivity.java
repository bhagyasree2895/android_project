package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class uploadImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
    }

    public void gotoPostingAvailAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, PostingAvailabilityActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
