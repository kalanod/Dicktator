package com.kalanco.dictator.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kalanco.dictator.ActivityLogin;
import com.kalanco.dictator.R;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;


public class FragmentSettings extends Fragment {
    Button buttonLogout, btnRefresh, btnSound;
    private LocalDatabaseService mDBHelper;

    public FragmentSettings(LocalDatabaseService mDBHelper
    ) {
        this.mDBHelper = mDBHelper;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        buttonLogout = view.findViewById(R.id.btnLogout2);
        btnSound = view.findViewById(R.id.btnSound);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("Подтверди выход");
                builder.setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setPositiveButton("выход", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserService.logout();
                        startActivity(new Intent(getActivity(), ActivityLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        try {
                            finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                builder.show();
            }
        });
        btnRefresh = view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
                builder.setTitle("будет сброшен весь прогресс");
                builder.setNegativeButton("отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                builder.setPositiveButton("сброс", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserService.refreshData(mDBHelper);
                        Toast.makeText(getActivity(), "Игровые данные сброшены", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
        checkSound();
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBHelper.changeSound();
                checkSound();
            }
        });
        return view;
    }
    void checkSound(){
        btnSound.setText("звук выключен");
        if (mDBHelper.isSoundOn()) {
            btnSound.setText("звук включён");
        }
    }
    public void callParentMethod() {
        getActivity().onBackPressed();
    }
}