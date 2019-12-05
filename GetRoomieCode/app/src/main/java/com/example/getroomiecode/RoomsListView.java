package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RoomsListView extends AppCompatActivity implements SearchView.OnQueryTextListener {
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<RoomItem> roomList;
    private GestureDetector mDetector;
    public String name;
    public String cost;
    private List<ParseObject> lastResult=new ArrayList<ParseObject>();
    public int reqcode=5;
    public ParseQuery<ParseObject> query;
    public String filteravailability;
    public String costRange, gender, location;
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
        Intent locationIntent=getIntent();
        location=locationIntent.getStringExtra("location");
         query= ParseQuery.getQuery("Room");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                lastResult = objects;
                roomList = new ArrayList<>();
                for(ParseObject po:objects){

                    if(po.getString("Address").toLowerCase().contains(location.toLowerCase())){
                        name=po.getString("Address");
                        cost=po.getString("Cost");
                        roomList.add(new RoomItem(R.drawable.roomingone, name, cost+"$"));
                    }
                    else if(location.equals("null")){
                        name=po.getString("Address");
                        cost=po.getString("Cost");
                        roomList.add(new RoomItem(R.drawable.roomingone, name, cost+"$"));
                    }

                    mRecyclerView = findViewById(R.id.recyclerView);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(RoomsListView.this);
                    adapter = new ItemAdapter(roomList,RoomsListView.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });

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
            Intent toOtherIntent = new Intent(this, FilterActivity.class);
            Log.d("reqCode",""+reqcode);
            this.startActivityForResult(toOtherIntent,reqcode);


           // Intent newIntent=getIntent();
//            int Availability=newIntent.getIntExtra("availability",1);
//            int costRange=newIntent.getIntExtra("costValue",100);
//            String gender=newIntent.getStringExtra("genderPreference");
//            ArrayList<RoomItem> newList=new ArrayList<>();
//            if(gender.equals("Male")){



        }
      return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==reqcode && resultCode==10){
             filteravailability = data.getStringExtra("availability");
           // Log.d("filteravailability", "" + filteravailability);
              costRange = data.getStringExtra("costValue");
        Log.d("costRange", "" + costRange);
             gender = data.getStringExtra("genderPreference");
           // Log.d("genderPreference", "" + gender);
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Room");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    lastResult = objects;
                    roomList = new ArrayList<>();
                    for (ParseObject po : objects) {
                        int dataAvaialability = Integer.parseInt(po.getString("Availability"));
                       // Log.d("dataAvailability", "" + dataAvaialability+"filter "+Integer.parseInt(filteravailability));

                        if (filteravailability.equals("0") && Integer.parseInt(costRange)>=Integer.parseInt(po.getString("Cost")) && gender.equals("null")) {
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));

                        }
                        else if (dataAvaialability >= Integer.parseInt(filteravailability) && Integer.parseInt(costRange)==0 && gender.equals("null")) {
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));

                        }
                        else if(filteravailability.equals("0") && Integer.parseInt(costRange)==0 && gender.equals(po.getString("genderPreference"))){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }
                        else if(filteravailability.equals("0") && Integer.parseInt(costRange)>=Integer.parseInt(po.getString("Cost")) && gender.equals(po.getString("genderPreference"))){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }
                        else if(dataAvaialability >= Integer.parseInt(filteravailability) && Integer.parseInt(costRange)>=Integer.parseInt(po.getString("Cost")) && gender.equals("null")){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }
                        else if(dataAvaialability >= Integer.parseInt(filteravailability) && Integer.parseInt(costRange)==0 && gender.equals(po.getString("genderPreference"))){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }
                        else if(dataAvaialability >= Integer.parseInt(filteravailability) && Integer.parseInt(costRange)>=Integer.parseInt(po.getString("Cost")) && gender.equals(po.getString("genderPreference"))){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }
                        else if(filteravailability.equals("0") && Integer.parseInt(costRange)==0 && gender.equals("null")){
                            name=po.getString("Address");
                            cost=po.getString("Cost");
                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
                        }


                        mRecyclerView = findViewById(R.id.recyclerView);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(RoomsListView.this);
                        adapter = new ItemAdapter(roomList, RoomsListView.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                        Log.d("check", String.valueOf(dataAvaialability));
//                        else if(filteravailability==null){}
//                        int Datacost = Integer.parseInt(po.getString("Cost"));
 //                       Log.d("datacost", "" + Datacost);
//                        if(Datacost<=Integer.parseInt(costRange)){
//                            roomList.add(new RoomItem(R.drawable.roomingone, name, cost));
//                            mRecyclerView = findViewById(R.id.recyclerView);
//                            mRecyclerView.setHasFixedSize(true);
//                            mLayoutManager = new LinearLayoutManager(RoomsListView.this);
//                            adapter = new ItemAdapter(roomList, RoomsListView.this);
//                            mRecyclerView.setLayoutManager(mLayoutManager);
//                            mRecyclerView.setAdapter(adapter);
//                            Log.d("check", String.valueOf(Datacost));
//                        }
//                        else if(costRange==null){}
//                    }
                    }
                }
            });
        }}




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

        }
        adapter = new ItemAdapter(newList,RoomsListView.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return true;
    }
}
