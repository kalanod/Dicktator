package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.AchievAdapter;
import com.kalanco.dictator.adapters.FriendsAdapter;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.util.Locale;


public class FragmentProfile extends Fragment {
    private LocalDatabaseService mDBHelper;
    private FriendsAdapter friendAdapter;
    private AchievAdapter achievAdapter;
    FragmentFriend fragmentFriend;

    public FragmentProfile(LocalDatabaseService service) {
        mDBHelper = service;
        fragmentFriend = new FragmentFriend();
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
        best.setText(String.format(Locale.US, "%d", mDBHelper.getBest()));
        View.OnClickListener friendListerner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, fragmentFriend);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };
        friendAdapter = new FriendsAdapter(DatabaseService.getFriendsOptions(UserService.getFUserId()), friendListerner);
        LinearLayoutManager friendManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        RecyclerView recyclerView = view.findViewById(R.id.friends_list);
        recyclerView.setAdapter(friendAdapter);
        recyclerView.setLayoutManager(friendManager);

        achievAdapter = new AchievAdapter(DatabaseService.getAchievOptions(UserService.getFUserId()));
        LinearLayoutManager achievManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        RecyclerView recyclerViewAchiev = view.findViewById(R.id.achiev_list);
        recyclerViewAchiev.setAdapter(achievAdapter);
        recyclerViewAchiev.setLayoutManager(achievManager);

        friendAdapter.startListening();
        achievAdapter.startListening();
        return view;
    }

    public void callParentMethod() {
        getActivity().onBackPressed();
    }

    public void onStop() {
        super.onDestroy();
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        friendAdapter.stopListening();
        achievAdapter.stopListening();
    }
}