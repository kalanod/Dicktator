package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.FriendsAdapter;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.UserService;

public class FragmentAddFriends extends Fragment {
    private FriendsAdapter friendAdapter;

    public FragmentAddFriends() {
        // Required empty public constructor
    }

    public static FragmentAddFriends newInstance(String param1, String param2) {
        FragmentAddFriends fragment = new FragmentAddFriends();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_friends, container, false);
        View.OnClickListener friendListerner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        return view;
    }
}