package com.example.getroomiecode;

import android.content.Context;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private ArrayList<RoomItem> roomList;
    private List<RoomItem> CompleteRoomList;
    private Context context;
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
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        RoomItem currentItem = roomList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(context,Room1DetailsActivity.class);
                
                context.startActivity(in);

            }
        });
    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }


}