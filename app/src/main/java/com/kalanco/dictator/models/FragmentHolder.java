package com.kalanco.dictator.models;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kalanco.dictator.R;
import com.kalanco.dictator.fragments.FragmentAddFriends;
import com.kalanco.dictator.fragments.FragmentEdit;
import com.kalanco.dictator.fragments.FragmentFriend;
import com.kalanco.dictator.fragments.FragmentProfile;
import com.kalanco.dictator.services.LocalDatabaseService;

public class FragmentHolder {
    public FragmentFriend fragmentFriend;
    public FragmentAddFriends fragmentAddFriends;
    public FragmentProfile fragmentProfile;
    public LocalDatabaseService mDBHelper;
    public FragmentManager fragmentManager;
    public FragmentEdit fragmentEdit;

    public FragmentHolder(FragmentFriend fragmentFriend, FragmentAddFriends fragmentAddFriends, FragmentProfile fragmentProfile, LocalDatabaseService mDBHelper, FragmentEdit fragmentEdit, FragmentManager fragmentManager) {
        this.fragmentFriend = fragmentFriend;
        this.fragmentAddFriends = fragmentAddFriends;
        this.fragmentProfile = fragmentProfile;
        this.mDBHelper = mDBHelper;
        this.fragmentManager = fragmentManager;
        this.fragmentEdit = fragmentEdit;
    }
    public void changeToProfile() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentProfile);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void changeToFriend() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentFriend);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void changeToAddFriends() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentAddFriends);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void changeToEdit() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentEdit);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
