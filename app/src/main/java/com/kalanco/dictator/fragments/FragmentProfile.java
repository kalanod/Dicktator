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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.AchievAdapter;
import com.kalanco.dictator.adapters.FriendsAdapter;
import com.kalanco.dictator.models.FragmentHolder;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.LocalDatabaseService;
import com.kalanco.dictator.services.UserService;

import java.util.Locale;


public class FragmentProfile extends Fragment {
    private FriendsAdapter friendAdapter;
    private AchievAdapter achievAdapter;
    Button addFriend;
    FragmentHolder fragmentHolder;
    ImageButton btnEdit;
    ImageView ava;
    View view;
    TextView textName, best;
    RecyclerView recyclerView, recyclerViewAchiev;

    public FragmentProfile() {
    }

    public void setFragmentHolder(FragmentHolder holder) {
        this.fragmentHolder = holder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        linker();
        setAva(fragmentHolder.mDBHelper.getImg());
        textName.setText(fragmentHolder.mDBHelper.getName());

        best.setText("Рекод:" + fragmentHolder.mDBHelper.getBest());

        friendAdapter = new FriendsAdapter(DatabaseService.getFriendsOptions(UserService.getFUserId()), fragmentHolder);
        LinearLayoutManager friendManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setAdapter(friendAdapter);
        recyclerView.setLayoutManager(friendManager);

        achievAdapter = new AchievAdapter(DatabaseService.getAchievOptions(UserService.getFUserId()));
        LinearLayoutManager achievManager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewAchiev.setAdapter(achievAdapter);
        recyclerViewAchiev.setLayoutManager(achievManager);

        friendAdapter.startListening();
        achievAdapter.startListening();

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.changeToAddFriends();
            }
        });
        view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.changeToEdit();
            }
        });
        return view;
    }

    private void linker() {
        textName = view.findViewById(R.id.text_name);
        best = view.findViewById(R.id.text_best);
        recyclerView = view.findViewById(R.id.friends_list);
        recyclerViewAchiev = view.findViewById(R.id.achiev_list);
        addFriend = view.findViewById(R.id.btn_add_friend);
        ava = view.findViewById(R.id.imageView2);
    }

    public void onDestroy() {

        friendAdapter.stopListening();
        achievAdapter.stopListening();
        super.onDestroy();
    }
    private void setAva(int current) {
        switch (current) {
            case 0:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar));
                break;
            case 1:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar1));
                break;
            case 2:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar2));
                break;
            case 3:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar3));
                break;
            case 4:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar4));
                break;
            case 5:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar5));
                break;
            case 6:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar6));
                break;
        }
    }
}