package com.kalanco.dictator.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.kalanco.dictator.R;
import com.kalanco.dictator.adapters.AchievAdapter;
import com.kalanco.dictator.models.User;
import com.kalanco.dictator.services.DatabaseService;
import com.kalanco.dictator.services.UserService;

public class FragmentFriend extends Fragment {
    FragmentAddFriends fragmentAddFriend;
    AchievAdapter achievAdapter;
    ImageButton back;
    TextView name, best, name2;
    User user;
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
                change(fragmentAddFriend);
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
            }
        });
        return view;
    }

    public void linker(View view) {
        back = view.findViewById(R.id.buttonBack);
        name = view.findViewById(R.id.text_name);
        name2 = view.findViewById(R.id.text_name2);
        best = view.findViewById(R.id.text_best);


    }

    public void change(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void onDestroy() {
        achievAdapter.stopListening();
        super.onDestroy();

    }

}