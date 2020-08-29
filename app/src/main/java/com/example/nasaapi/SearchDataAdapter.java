package com.example.nasaapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.MyViewHolder> {

    public Context mContext;
    public ArrayList<SearchData> dataArrayList;

    public SearchDataAdapter(Context mContext, ArrayList<SearchData> dataArrayList) {
        this.mContext = mContext;
        this.dataArrayList = dataArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.data__layout , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title_view.setText(dataArrayList.get(position).getmTitle());
        holder.desc_view.setText(dataArrayList.get(position).getmDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , SearchDetails.class);
                intent.putExtra("id" , dataArrayList.get(position).getmNasa_id());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_view , desc_view ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_view = itemView.findViewById(R.id.title_view);
            desc_view = itemView.findViewById(R.id.desc_view);
        }
    }
}
