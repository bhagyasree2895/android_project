package com.example.getroomiecode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>  implements Filterable {
    public ArrayList<RoomItem> roomList;
    private List<RoomItem> roomListFull;



    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }
    }


    public ItemAdapter(ArrayList<RoomItem> roomList) {
        this.roomList = roomList;
        roomListFull =new ArrayList<>(roomList);
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
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }
    @Override
    public Filter getFilter() {
       return ItemFilter;
    }
    private Filter ItemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RoomItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(roomListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RoomItem item : roomListFull) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                    else if(item.getText1().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            roomList.clear();
            roomList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}