package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.FriendsAdapter;
import com.kalanco.dictator.models.FragmentHolder;
import com.kalanco.dictator.services.DatabaseService;

public class FragmentAddFriends extends Fragment {
    private FriendsAdapter friendAdapter;
    FragmentFriend fragmentFriend;
    FragmentProfile fragmentProfile;
    ImageButton back;
    FragmentHolder fragmentHolder;

    public FragmentAddFriends() {
        fragmentFriend = new FragmentFriend();
        fragmentProfile = new FragmentProfile();
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

        friendAdapter = new FriendsAdapter(DatabaseService.getUsersOptions(), fragmentHolder);
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
        back = view.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.changeToProfile();
            }
        });
        return view;
    }
    public void onStop() {
        friendAdapter.stopListening();
        super.onStop();
    }
    public void onStart() {
        super.onStart();
        friendAdapter.startListening();

    }
    public void setFragmentHolder(FragmentHolder holder) {
        this.fragmentHolder = holder;
    }
}