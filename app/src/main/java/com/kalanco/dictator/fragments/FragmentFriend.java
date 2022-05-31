package com.kalanco.dictator.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.AchievAdapter;
import com.kalanco.dictator.models.FragmentHolder;
import com.kalanco.dictator.models.User;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.UserService;

public class FragmentFriend extends Fragment {
    FragmentAddFriends fragmentAddFriend;
    AchievAdapter achievAdapter;
    ImageButton back;
    TextView name, best, name2;
    User user;
    FragmentHolder fragmentHolder;
    Button addFriends;
    View.OnClickListener addListener;
    ImageView ava;
    boolean isFriend;
    public String id;

    public FragmentFriend() {
    }

    public FragmentFriend(FragmentAddFriends ff) {
        fragmentAddFriend = ff;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        linker(view);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.changeToAddFriends();
            }
        });
        achievAdapter = new AchievAdapter(DatabaseService.getAchievOptions(id));
        LinearLayoutManager achievManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerViewAchiev = view.findViewById(R.id.achiev_list);
        recyclerViewAchiev.setAdapter(achievAdapter);
        recyclerViewAchiev.setLayoutManager(achievManager);
        DatabaseService.getDatabaseUser(id).addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.name);
                name2.setText(user.name);
                best.setText(Integer.toString(user.best));
                achievAdapter.startListening();
                if (UserService.isFriend(user.id, FragmentFriend.this)) {
                    addFriends.setText("Удалить друга");
                    addFriends.setBackgroundColor(Color.RED);
                }
                addFriends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFriend) {
                            UserService.removeFriend(user);
                            notFriend();
                        } else {
                            UserService.addFriend(user);
                            friend();
                        }
                    }
                });
                setAva(user.img);
            }
        });

        return view;
    }

    public void linker(View view) {
        back = view.findViewById(R.id.btn_back);
        name = view.findViewById(R.id.text_name);
        name2 = view.findViewById(R.id.text_name2);
        best = view.findViewById(R.id.text_best);
        addFriends = view.findViewById(R.id.btn_add_friend);
        ava = view.findViewById(R.id.imageView2);

    }

    public void onDestroy() {
        achievAdapter.stopListening();
        super.onDestroy();

    }

    public void setFragmentHolder(FragmentHolder holder) {
        this.fragmentHolder = holder;
    }

    public void friend() {
        addFriends.setText("Удалить друга");
        addFriends.setBackgroundColor(Color.RED);
        isFriend = true;
    }

    public void notFriend() {
        addFriends.setText("Добавить в друзья");
        addFriends.setBackgroundColor(getActivity().getResources().getColor(R.color.purple_200));
        isFriend = false;
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