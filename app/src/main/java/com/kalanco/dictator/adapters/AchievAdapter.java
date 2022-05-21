package com.kalanco.dictator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.kalanco.dictator.R;
import com.kalanco.dictator.models.Achiev;
import com.kalanco.dictator.models.Friend;

import java.util.LinkedList;
import java.util.List;

public class AchievAdapter extends FirebaseRecyclerAdapter<Achiev, AchievAdapter.viewHolder> {
    public List<Achiev> list = new LinkedList<>();

    public AchievAdapter(@NonNull FirebaseRecyclerOptions<Achiev> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Achiev model) {
        holder.buid(model);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achiv_item,
                parent,
                false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View card;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            card = itemView.findViewById(R.id.card);
        }

        public void buid(Achiev achiev) {
            title.setText(achiev.title);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), achiev.title, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
