package com.kalanco.dictator.adapters;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalanco.dictator.R;
import com.kalanco.dictator.models.Point;
import com.kalanco.dictator.services.LocalDatabaseService;

import java.util.ArrayList;
import java.util.List;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder>{
    public List<Point> list;
    private LocalDatabaseService mDBHelper;

    public PointAdapter(int count) {
        this.list = new ArrayList<>();
        for (int i = 0; i < count; i++){
            list.add(new Point(true));
        }
        for (int i = count; i < 5; i++){
            list.add(new Point(false));
        }
    }
    public void update(int count) {
        this.list.clear();
        for (int i = 0; i < count; i++){
            list.add(new Point(true));
        }
        for (int i = count; i < 5; i++){
            list.add(new Point(false));
        }
        notifyDataSetChanged();
    }
    public PointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item, parent, false);
        return new PointAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(PointAdapter.ViewHolder holder, int position) {
        Point item = list.get(position);
        if (item.isCheckt) {
            holder.isCheckt.setBackgroundColor(Color.parseColor("#FFCE2CEA"));
            return;
        }
        holder.isCheckt.setBackgroundColor(Color.parseColor("#FFCCCCCC"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View isCheckt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isCheckt = itemView.findViewById(R.id.view7);
        }
    }
}
