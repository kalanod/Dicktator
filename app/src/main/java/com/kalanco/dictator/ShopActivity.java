package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kalanco.dictator.adapters.LocalShopAdapter;
import com.kalanco.dictator.adapters.ShopAdapter;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.io.IOException;

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LocalShopAdapter adapter;
    //UsersListAdapter adapter;
    ImageButton bntBack;
    private LocalDatabaseService mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        linker();
        //adapter = new ShopAdapter(DatabaseService.getShopOptions(UserService.getFUserId()));
        bindDatabase();
        adapter = new LocalShopAdapter(mDBHelper.getListItem(), mDBHelper);
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
    public void onDestroy() {
        mDBHelper.close();
        super.onDestroy();
    }
    void linker() {
        recyclerView = findViewById(R.id.list);
        bntBack = findViewById(R.id.buttonBack);
        //buttonBack = findViewById(R.id.buttonBack);
    }

    void bindDatabase() {
        mDBHelper = new LocalDatabaseService(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }
}