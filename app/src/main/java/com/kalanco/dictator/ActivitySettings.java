package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kalanco.dictator.services.UserService;

public class ActivitySettings extends AppCompatActivity {
    Button buttonLogout, btnRefresh;
    ImageButton buttonBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        linker();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivitySettings.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.logout();
                startActivity(new Intent(ActivitySettings.this,ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.refreshData();
            }
        });
    }
    void linker() {
        buttonLogout = findViewById(R.id.btnLogout);
        buttonBack = findViewById(R.id.buttonBack);
        btnRefresh = findViewById(R.id.btnRefresh);
    }
}