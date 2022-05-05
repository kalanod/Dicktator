package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kalanco.dictator.adapters.ShopAdapter;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.UserService;

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ShopAdapter adapter;
    //UsersListAdapter adapter;
    ImageButton bntBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        linker();
        adapter = new ShopAdapter(DatabaseService.getShopOptions(UserService.getFUserId()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        bntBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this, MainActivity.class));
            }
        });


    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }
    void linker() {
        recyclerView = findViewById(R.id.list);
        bntBack = findViewById(R.id.buttonBack);
        //buttonBack = findViewById(R.id.buttonBack);
    }
}