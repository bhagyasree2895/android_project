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
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RoomsListView extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<RoomItem> roomList;
    private GestureDetector mDetector;
    private List<ParseObject> lastResult=new ArrayList<ParseObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        setContentView(R.layout.activity_rooms_list_view);
        roomList = new ArrayList<>();
        ParseQuery<ParseObject> query= ParseQuery.getQuery("Room");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                lastResult = objects;

                for(ParseObject po:objects){
                    String name=po.getString("Address");

                }
            }
        });
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
