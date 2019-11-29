package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RoomCompleteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_complete_details);
        TextView aptTypeTV=findViewById(R.id.textView9);
        TextView availability=findViewById(R.id.textView11);
        TextView aptAddress=findViewById(R.id.textView4);
        Intent intent=getIntent();
        aptTypeTV.setText(intent.getStringExtra("aptType"));
        availability.setText(intent.getStringExtra("availability"));
        aptAddress.setText(intent.getStringExtra("aptAddress"));

    }

    public void gotosignoutAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, MainActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotohomePage(View v) {
        try {
            Intent toOtherIntent = new Intent(this,SignInActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotoSearchResultsImagesAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SearchResultsImagesActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotoContactAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, ContactActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
