package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

    }
    public void getLocationAction(View v) {
        Intent ini = new Intent(this, MapsActivity.class);
        startActivityForResult(ini,11);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent roomInt) {
        super.onActivityResult(requestCode, resultCode, roomInt);
        if (requestCode == 11) {
            if (resultCode == 11) {
                String str = roomInt.getStringExtra("LocationName");
                TextView incidentLocTV = findViewById(R.id.locationTV);
                incidentLocTV.setText(str);
            }
        }
    }
    //Search room availability
    public void gotoSearchRoom(View v) {
        try {
            Intent toOtherIntent = new Intent(this,RoomsListView.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }


    }

    //Post Availability action
    public void gotoPostingAvailAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, PostingAvailabilityActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }


    }
    public void gotosignoutAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, MainActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void onSearch(View v) {
        try {
            Intent toOtherIntent = new Intent(this, SearchResultActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
