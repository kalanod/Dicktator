package com.kalanco.dictator.services;

import com.firebase.ui.database.ClassSnapshotParser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kalanco.dictator.models.Achiev;
import com.kalanco.dictator.models.Friend;
import com.kalanco.dictator.models.ShopItem;
import com.kalanco.dictator.models.User;

public class DatabaseService {

    public static FirebaseDatabase getDatabase(){
        return FirebaseDatabase.getInstance("https://dictator-55706-default-rtdb.europe-west1.firebasedatabase.app/");}
    public static Task<Void> storeUser(User user){
        return getDatabase().getReference("users").child(user.id).setValue(user);
    }
    public static Task<Void> dellUser(String id){
        return getDatabase().getReference("users/" + id).removeValue();
    }
    public static Task<Void> editUser(User user){
        return getDatabase().getReference("users/" + user.id).setValue(user);
    }

    public static void getMoneyListerner(String id, ValueEventListener listerner) {
        getDatabase().getReference("users/" + id + "/money").addValueEventListener(listerner);
    }
    public static FirebaseRecyclerOptions<ShopItem> getShopOptions(String id){
        Query quere = getDatabase().getReference("users/" + id + "/builds");
        ClassSnapshotParser<ShopItem> parser = new ClassSnapshotParser<>(ShopItem.class);
        return new FirebaseRecyclerOptions.Builder<ShopItem>().setQuery(quere, parser).build();
    }


    public static Task<Void> buy(String fUserId, String id) {
        return getDatabase().getReference("users/" + fUserId + "/builds/" + id +"/isBought").setValue(true);
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
}
//Kalan kalan1 = new Kalan(10);
//kalan1.getHeight();
//Kalan.isFood(); // static
//Kalan kalan2 = new Kalan(20);