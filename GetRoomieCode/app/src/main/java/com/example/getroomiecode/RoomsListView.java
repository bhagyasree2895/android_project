package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import java.util.ArrayList;

public class RoomsListView extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<RoomItem> roomList;
    private GestureDetector mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list_view);
        roomList = new ArrayList<>();
        roomList.add(new RoomItem(R.drawable.roomingone, "Horizons", "250$"));
        roomList.add(new RoomItem(R.drawable.roomingtwo, "Village O", "190$"));
        roomList.add(new RoomItem(R.drawable.roomingone, "FoxAlley", "210$"));
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(RoomsListView.this);
        adapter = new ItemAdapter(roomList,RoomsListView.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.room_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId()==R.id.filter){
            Intent toOtherIntent = new Intent(this, filterActivity.class);
            startActivity(toOtherIntent);
        }
      return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        String userInput=query.toLowerCase();
        ArrayList<RoomItem> newList=new ArrayList<>();
        for(RoomItem item : roomList)
        {
            if(item.getText1().toLowerCase().contains(userInput))
            {
                newList.add(item);
            }
            else if(item.getText2().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }
        adapter = new ItemAdapter(newList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return true;
    }
}
