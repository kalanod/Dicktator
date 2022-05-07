package com.kalanco.dictator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kalanco.dictator.fragments.FragmentHome;
import com.kalanco.dictator.fragments.FragmentProfile;
import com.kalanco.dictator.fragments.FragmentSettings;

public class ActivityHome extends AppCompatActivity {
    BottomNavigationView navigationView;
    FragmentSettings fragmentSettings = new FragmentSettings();
    FragmentHome fragmentHome = new FragmentHome();
    FragmentProfile fragmentProfile = new FragmentProfile();
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        linker();
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
    }

    void change(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}