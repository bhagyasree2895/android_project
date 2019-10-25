package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        ArrayList<RoomItem> roomList = new ArrayList<>();
        roomList.add(new RoomItem(R.drawable.roomingone, "Horizons", "250$"));
        roomList.add(new RoomItem(R.drawable.roomingtwo, "Village O", "190$"));
        roomList.add(new RoomItem(R.drawable.roomingone, "FoxAlley", "210$"));
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ItemAdapter(roomList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.room_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              //  adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
//    //Post Availability action
//    public void gotoPostingAvailAction(View v) {
//        try {
//            Intent toOtherIntent = new Intent(this, PostingAvailabilityActivity.class);
//            startActivity(toOtherIntent);
//
//        } catch (Exception e) {
//
//        }
//
//
//    }
//    public void gotosignoutAction(View v) {
//        try {
//            Intent toOtherIntent = new Intent(this, MainActivity.class);
//            startActivity(toOtherIntent);
//
//        } catch (Exception e) {
//
//        }
//    }
//    public void onSearch(View v) {
//        try {
//            Intent toOtherIntent = new Intent(this, SearchResultActivity.class);
//            startActivity(toOtherIntent);
//
//        } catch (Exception e) {
//
//        }
//    }
}
