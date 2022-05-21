package com.kalanco.dictator.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kalanco.dictator.ActivityHome;
import com.kalanco.dictator.MainActivity;
import com.kalanco.dictator.R;
import com.kalanco.dictator.services.LocalDatabaseService;


public class FragmentHome extends Fragment {
    Button button;
    private LocalDatabaseService mDBHelper;
    public FragmentHome(LocalDatabaseService mDBHelper) {
        this.mDBHelper = mDBHelper;
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        button = view.findViewById(R.id.btn_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return view;
    }
    public void callParentMethod(){
        getActivity().onBackPressed();
    }
}