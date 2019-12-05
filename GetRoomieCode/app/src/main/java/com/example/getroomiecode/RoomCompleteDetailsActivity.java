package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class RoomCompleteDetailsActivity extends AppCompatActivity {

    public ParseQuery<ParseObject> query;
    public static String username;
    public static String userNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_complete_details);
        final TextView aptTypeTV=findViewById(R.id.textView9);
        TextView availability=findViewById(R.id.textView11);
        final TextView aptAddress=findViewById(R.id.textView4);
        TextView cost=findViewById(R.id.textView12);
        Intent intent=getIntent();
        aptTypeTV.setText(intent.getStringExtra("aptType"));
        availability.setText(intent.getStringExtra("availability"));
        aptAddress.setText(intent.getStringExtra("aptAddress"));
        cost.setText(intent.getStringExtra("Cost")+"$");
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        query= ParseQuery.getQuery("Room");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject po:objects){
                    String aptType= String.valueOf(aptTypeTV.getText());
                    String aptaddress=String.valueOf(aptAddress.getText());
                    if(aptaddress.equals(po.getString("Address"))){
                        if(aptType.equals(po.getString("AptType"))) {
                            username=po.getString("Tenant");
                            userNum=po.getString("Mobile");
                        }
                    }

                }
            }
        });
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
            toOtherIntent.putExtra("userName",username);
            toOtherIntent.putExtra("userNum",""+userNum);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
}
