package com.kalanco.dictator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.kalanco.dictator.services.UserService;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }
    protected void onStart() {
        super.onStart();
        if (UserService.isLogin()){
            startActivity(new Intent(ActivitySplash.this, ActivityHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        else {
            startActivity(new Intent(ActivitySplash.this, ActivityReg.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}