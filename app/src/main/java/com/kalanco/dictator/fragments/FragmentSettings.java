package com.kalanco.dictator.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.kalanco.dictator.ActivityLogin;
import com.kalanco.dictator.ActivitySettings;
import com.kalanco.dictator.MainActivity;
import com.kalanco.dictator.R;
import com.kalanco.dictator.services.UserService;


public class FragmentSettings extends Fragment {
    Button buttonLogout, btnRefresh;
    public FragmentSettings() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);

        buttonLogout = view.findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.logout();
                startActivity(new Intent(getActivity(), ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.refreshData();
            }
        });
        return view;
    }
}