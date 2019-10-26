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
import android.widget.SearchView;

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
