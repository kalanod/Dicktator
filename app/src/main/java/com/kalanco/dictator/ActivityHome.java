package com.kalanco.dictator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kalanco.dictator.fragments.FragmentHome;
import com.kalanco.dictator.fragments.FragmentProfile;
import com.kalanco.dictator.fragments.FragmentSettings;
import com.kalanco.dictator.services.LocalDatabaseService;

import java.io.IOException;

public class ActivityHome extends AppCompatActivity {
    private LocalDatabaseService mDBHelper;
    BottomNavigationView navigationView;
    FragmentSettings fragmentSettings;
    FragmentHome fragmentHome;
    FragmentProfile fragmentProfile;
    Button btnStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);
        bindDatabase();
        linker();
        navigationView.setSelectedItemId(R.id.home);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        change(fragmentHome);
                        return true;
                    case R.id.account:
                        change(fragmentProfile);
                        return true;
                    case R.id.settings:
                        change(fragmentSettings);
                        return true;
                }
                return false;
            }
        });
        change(fragmentHome);
    }

    private void linker() {
        navigationView = findViewById(R.id.bottomNavigationView);
        fragmentSettings = new FragmentSettings();
        fragmentHome = new FragmentHome();
        fragmentProfile = new FragmentProfile(mDBHelper);
    }

    void change(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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