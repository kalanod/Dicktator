package com.kalanco.dictator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.kalanco.dictator.R;
import com.kalanco.dictator.models.Friend;

import java.util.LinkedList;
import java.util.List;

public class FriendsAdapter extends FirebaseRecyclerAdapter<Friend, FriendsAdapter.viewHolder> {
    public List<Friend> list = new LinkedList<>();
    View.OnClickListener listerner;

    public FriendsAdapter(@NonNull FirebaseRecyclerOptions<Friend> options, View.OnClickListener listener1) {
        super(options);
        listerner = listener1;

    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Friend model) {
        holder.buid(model);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,
                parent,
                false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View view;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_title);
            view = itemView.findViewById(R.id.card);
        }

        public void buid(Friend user) {
            name.setText(user.name);
            view.setOnClickListener(listerner);
        }
    }
}
