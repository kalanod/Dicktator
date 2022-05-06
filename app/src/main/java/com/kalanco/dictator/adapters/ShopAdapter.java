package com.kalanco.dictator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.kalanco.dictator.R;
import com.kalanco.dictator.models.ShopItem;
import com.kalanco.dictator.services.UserService;

import java.util.LinkedList;
import java.util.List;

public class ShopAdapter extends FirebaseRecyclerAdapter<ShopItem, ShopAdapter.viewHolder> {
    public List<ShopItem> list = new LinkedList<>();

    public ShopAdapter(@NonNull FirebaseRecyclerOptions<ShopItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull ShopItem model) {
        holder.buid(model);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item,
                parent,
                false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name, desk;
        Button buy;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fild_name);
            desk = itemView.findViewById(R.id.fild_desc);
            buy = itemView.findViewById(R.id.btnBuy);

        }

        public void buid(ShopItem user) {
            name.setText(user.name);
            desk.setText(user.desc);
            if (user.isBought) {
                buy.setClickable(false);
                buy.setText("КУПЛЕНО");
            }
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserService.buy(Integer.toString(user.id)).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(itemView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            buy.setClickable(false);
                            buy.setText("КУПЛЕНО");
                        }
                    });
                    //Toast.makeText(itemView.getContext(), "sdf", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
