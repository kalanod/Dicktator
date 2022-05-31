package com.kalanco.dictator.services;

import android.util.Log;

import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kalanco.dictator.fragments.FragmentFriend;
import com.kalanco.dictator.models.Achiev;
import com.kalanco.dictator.models.Friend;
import com.kalanco.dictator.models.ShopItem;
import com.kalanco.dictator.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseService {
    public static boolean flag;

    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance("https://dictator-55706-default-rtdb.europe-west1.firebasedatabase.app/");
    }

    public static Task<Void> storeUser(User user) {
        return getDatabase().getReference("users").child(user.id).setValue(user);
    }

    public static Task<Void> dellUser(String id) {
        return getDatabase().getReference("users/" + id).removeValue();
    }

    public static Task<Void> editUser(User user) {
        return getDatabase().getReference("users/" + user.id).setValue(user);
    }

    public static void getMoneyListerner(String id, ValueEventListener listerner) {
        getDatabase().getReference("users/" + id + "/money").addValueEventListener(listerner);
    }

    public static FirebaseRecyclerOptions<ShopItem> getShopOptions(String id) {
        Query quere = getDatabase().getReference("users/" + id + "/builds");
        ClassSnapshotParser<ShopItem> parser = new ClassSnapshotParser<>(ShopItem.class);
        return new FirebaseRecyclerOptions.Builder<ShopItem>().setQuery(quere, parser).build();
    }


    public static Task<Void> buy(String fUserId, String id) {
        return getDatabase().getReference("users/" + fUserId + "/builds/" + id + "/isBought").setValue(true);
    }

    public static Task<DataSnapshot> getDatabaseUser(String fUserId) {
        return getDatabase().getReference("users/" + fUserId).get();
    }

    public static FirebaseRecyclerOptions<Friend> getFriendsOptions(String id) {
        Query quere = getDatabase().getReference("users").child(id).child("friends");
        ClassSnapshotParser<Friend> parser = new ClassSnapshotParser<>(Friend.class);
        return new FirebaseRecyclerOptions.Builder<Friend>().setQuery(quere, parser).build();
    }

    public static FirebaseRecyclerOptions<Achiev> getAchievOptions(String id) {
        Query quere = getDatabase().getReference("users").child(id).child("achivments");
        ClassSnapshotParser<Achiev> parser = new ClassSnapshotParser<>(Achiev.class);
        return new FirebaseRecyclerOptions.Builder<Achiev>().setQuery(quere, parser).build();
    }

    public static Task<DataSnapshot> getUser(String id) {
        return getDatabase().getReference("users/" + id).get();
    }

    public static FirebaseRecyclerOptions<Friend> getUsersOptions() {
        Query quere = getDatabase().getReference("users");
        ClassSnapshotParser<Friend> parser = new ClassSnapshotParser<>(Friend.class);
        return new FirebaseRecyclerOptions.Builder<Friend>().setQuery(quere, parser).build();
    }

    public static boolean isFriend(String id, String id2, FragmentFriend fragmentFriend) {
        flag = false;
        getDatabase().getReference("users/" + id + "/friends").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Friend i;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    i = snapshot.getValue(Friend.class);
                    if (i.id.equals(id2)) {
                        fragmentFriend.friend();
                        return;
                    }
                    fragmentFriend.notFriend();
                }
            }
        });
        Log.e("EEE2", Boolean.toString(flag));

        return flag;
    }

    public static void removeFriend(String id, String id2) {
        List<Friend> friends = new ArrayList<>();
        getDatabase().getReference("users/" + id + "/friends").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Friend i;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    i = snapshot.getValue(Friend.class);
                    if (!i.id.equals(id2)) {
                        friends.add(i);
                    }
                }
                getDatabase().getReference("users/" + id + "/friends").setValue(friends);
            }
        });
    }

    public static void addFriend(String id, Friend friend) {
        List<Friend> friends = new ArrayList<>();
        getDatabase().getReference("users/" + id + "/friends").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Friend i;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    i = snapshot.getValue(Friend.class);
                    friends.add(i);
                }
                friends.add(friend);
                getDatabase().getReference("users/" + id + "/friends").setValue(friends);
            }
        });
    }

    public static void setAva(String fUserId, int current) {
        getDatabase().getReference("users/" + fUserId + "/img").setValue(current);

    }

    public static void setName(String fUserId, String text) {
        getDatabase().getReference("users/" + fUserId + "/name").setValue(text);

    }

    public static void getAva(String fUserId, OnSuccessListener listener) {
        getDatabase().getReference("users/" + fUserId + "/img").get().addOnSuccessListener(listener);
    }
}
//Kalan kalan1 = new Kalan(10);
//kalan1.getHeight();
//Kalan.isFood(); // static
//Kalan kalan2 = new Kalan(20);