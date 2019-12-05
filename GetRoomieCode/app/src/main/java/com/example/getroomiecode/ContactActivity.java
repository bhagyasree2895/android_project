package com.example.getroomiecode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        TextView userName=findViewById(R.id.textView19);
        TextView userNum=findViewById(R.id.textView20);
        Intent intent=getIntent();
        //intent.getStringExtra("userName");
       userName.setText(intent.getStringExtra("userName"));
        userNum.setText(intent.getStringExtra("userNum"));
    }
    public void gotosignoutAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, MainActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void gotoHomePageAction(View v) {
        try {
            Intent toOtherIntent = new Intent(this, HomeActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }
    public void chatAction(View v){
        try{
            Intent toOtherIntent = new Intent(this, ChatActivity.class);
            startActivity(toOtherIntent);

        } catch (Exception e) {

        }
    }


}
