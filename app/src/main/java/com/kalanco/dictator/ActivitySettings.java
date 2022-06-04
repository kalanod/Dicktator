package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.io.IOException;

public class ActivitySettings extends AppCompatActivity {
    Button buttonLogout, btnRefresh, btnSound;
    ImageButton buttonBack;
    private LocalDatabaseService mDBHelper;

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
                startActivity(new Intent(ActivitySettings.this, ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        bindDatabase();
        checkSound();
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBHelper.changeSound();
                checkSound();
            }
        });

    }
    void checkSound(){
        btnSound.setText("звук выключен");
        if (mDBHelper.isSoundOn()) {
            btnSound.setText("звук включён");
        }
    }
    void linker() {
        buttonLogout = findViewById(R.id.btnLogout2);
        buttonBack = findViewById(R.id.btn_back);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnSound = findViewById(R.id.btnSound);
    }

    void bindDatabase() {
        mDBHelper = new LocalDatabaseService(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
    }

    public void onDestroy() {
        mDBHelper.close();
        super.onDestroy();
    }
}