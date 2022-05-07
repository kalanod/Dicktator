package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kalanco.dictator.R;
import com.kalanco.dictator.services.LocalDatabaseService;


public class FragmentProfile extends Fragment {
    private LocalDatabaseService mDBHelper;
    public FragmentProfile(LocalDatabaseService service) {
        mDBHelper = service;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView textName = view.findViewById(R.id.text_name);
        textName.setText(mDBHelper.getName());
        TextView best = view.findViewById(R.id.text_best);
        best.setText(mDBHelper.getBest());
        return view;
    }
    public void onDestroy() {
        super.onDestroy();
    }
}