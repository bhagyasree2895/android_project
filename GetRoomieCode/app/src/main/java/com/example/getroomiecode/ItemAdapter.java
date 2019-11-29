package com.example.getroomiecode;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    protected ArrayList<RoomItem> roomList;
    private List<RoomItem> CompleteRoomList;
    private Context context;
    public String aptType;
    public String availability;
    public String address;
    public int positionVal;
    private List<ParseObject> lastResult=new ArrayList<ParseObject>();
    public ItemAdapter(ArrayList<RoomItem> roomList, Context context){
        this.roomList=roomList;
        this.context=context;


    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public LinearLayout lt;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            lt=itemView.findViewById(R.id.linearlayout);

        }
    }


    public ItemAdapter(ArrayList<RoomItem> roomList) {
        this.roomList = roomList;
        CompleteRoomList =new ArrayList<>(roomList);
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false);
        ItemViewHolder evh = new ItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        RoomItem currentItem = roomList.get(position);
        positionVal=position;
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        //address=currentItem.getText1();
        holder.mTextView2.setText(currentItem.getText2());
        holder.lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("context", String.valueOf(context));
                Parse.initialize(context);
                ParseInstallation.getCurrentInstallation().saveInBackground();
                Parse.initialize(new Parse.Configuration.Builder(context)
                        .applicationId("Jp85UdwrCFJOrNJjij4ckORMgDkkq1sP8y0qGLAi")
                        // if defined
                        .clientKey("ycpRFcKrphpieDNnLkk8lhij76iX8L5zmWiyzj8l")
                        .server("https://parseapi.back4app.com/")
                        .build()
                );

                RoomItem selectedItem=roomList.get(holder.getAdapterPosition());
                address=selectedItem.getText1();
                ParseQuery<ParseObject> query= ParseQuery.getQuery("Room");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        lastResult = objects;

                        for(ParseObject po:objects){

                    if(po.getString("Address").equals(address)){
                        aptType=po.getString("AptType");
                        availability=po.getString("Availability");
                        String aptAddress=po.getString("Address");
                        Intent in=new Intent(context, RoomCompleteDetailsActivity.class);
                        in.putExtra("aptType",aptType);
                        in.putExtra("availability",availability);
                        in.putExtra("aptAddress",aptAddress);
                        context.startActivity(in);

                    }

                        }
                    }
                });



            }
        });
    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }


}