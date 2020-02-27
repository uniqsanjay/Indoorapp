package com.example.college_management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<ArrayList<String>> arr;

    public RecyclerAdapter(ArrayList<ArrayList<String>> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_search,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        ArrayList<String> arrayList = arr.get(position);

        // Checking if arrayList size > 0
        if (arrayList.size()>0){

            // Displaying UUID
            holder.uuid.setText(arrayList.get(0));

            //Displaying Major
            holder.major.setText(arrayList.get(1));

            //Displaying Minor
            holder.minor.setText(arrayList.get(2));

            //Displaying distance
            holder.distance.setText(arrayList.get(3));
        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView uuid;

        private TextView major;

        private TextView minor;

        private TextView distance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            uuid = itemView.findViewById(R.id.uuid);
            major = itemView.findViewById(R.id.major);
            minor = itemView.findViewById(R.id.minor);
            distance = itemView.findViewById(R.id.distance);
        }
    }
}
