package com.kalanco.dictator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalanco.dictator.MainActivity;
import com.kalanco.dictator.models.ShopItem;
import com.kalanco.dictator.R;
import com.kalanco.dictator.services.LocalDatabaseService;

import java.util.LinkedList;
import java.util.List;

public class LocalShopAdapter extends RecyclerView.Adapter<LocalShopAdapter.ViewHolder>{
    public List<ShopItem> list = new LinkedList<>();
    private LocalDatabaseService mDBHelper;

    public LocalShopAdapter(List<ShopItem> list, LocalDatabaseService mDBHelper) {
        this.list = list;
        this.mDBHelper = mDBHelper;
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ShopItem item = list.get(position);
        holder.desc.setText(item.desc);
        holder.name.setText(item.name);
        holder.btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDBHelper.canBuy(item.id)){
                    holder.btn_buy.setText("куплено");
                    mDBHelper.buy(item.id);
                }else {
                    Toast.makeText(v.getContext(), "Недостаточно золота", Toast.LENGTH_SHORT).show();
                };
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, desc;
        Button btn_buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fild_name);
            desc = itemView.findViewById(R.id.fild_desc);
            btn_buy = itemView.findViewById(R.id.btnBuy);
        }
    }
}
