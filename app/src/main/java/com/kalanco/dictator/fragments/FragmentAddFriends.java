package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.FriendsAdapter;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.UserService;

public class FragmentAddFriends extends Fragment {
    private FriendsAdapter friendAdapter;
    FragmentFriend fragmentFriend;
    FragmentProfile fragmentProfile;
    ImageButton back;
    FragmentTransaction transaction;

    public FragmentAddFriends() {
    }

    public FragmentAddFriends(FragmentFriend ff, FragmentProfile fp) {
        fragmentFriend = ff;
        fragmentProfile = fp;
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
        transaction = getActivity().getSupportFragmentManager().beginTransaction();

        friendAdapter = new FriendsAdapter(DatabaseService.getUsersOptions(), fragmentFriend, transaction);
        Log.d("tag", DatabaseService.getUsersOptions().toString());

        LinearLayoutManager friendManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        RecyclerView recyclerView = view.findViewById(R.id.friends_list);
        recyclerView.setAdapter(friendAdapter);
        recyclerView.setLayoutManager(friendManager);
        back = view.findViewById(R.id.buttonBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(fragmentProfile);
            }
        });
        return view;
    }
    public void onStop() {
        friendAdapter.stopListening();
        super.onStop();
    }
    public void onStart() {
        friendAdapter.startListening();
        super.onStart();

    }
    public void change(Fragment fragment){
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}