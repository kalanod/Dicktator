package com.kalanco.dictator.services;

import android.text.Editable;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.kalanco.dictator.fragments.FragmentFriend;
import com.kalanco.dictator.models.Achiev;
import com.kalanco.dictator.models.Friend;
import com.kalanco.dictator.models.User;

public class UserService {
    public static Task<AuthResult> storeUser(String name, String password, String email, LocalDatabaseService base) {
        Log.e("HERE", "HERE1");
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(authResult.getUser().getUid(), name, email);
                        DatabaseService.storeUser(user);
                        base.storeUser(user);
                        Log.e("HERE", "HERE2");
                    }
                });
    }

    public static Task<AuthResult> loginUser(String email, String password) {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
    }

    public static boolean isLogin() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    /*public static void dellUser(){
        DatabaseService.dellUser(getFUser().getUid());
        FirebaseAuth.getInstance().
    }*/
    public static String getFUserId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static FirebaseUser getFUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static Task<Void> buy(String id) {
        return DatabaseService.buy(getFUserId(), id);
    }

    public static void refreshData( LocalDatabaseService mDBHelper
    ) {
        mDBHelper.refresh();
        DatabaseService.setBest(getFUserId(), 0);
    }

    public static Task<DataSnapshot> getDatabaseUser() {
        return DatabaseService.getDatabaseUser(getFUserId());
    }
    public static void getDatabaseUser(String id) {
        DatabaseService.getDatabaseUser(id);
    }

    public static boolean isFriend(String id, FragmentFriend fragment) {
        return DatabaseService.isFriend(getFUserId(), id, fragment);
    }

    public static void removeFriend(User user) {
        DatabaseService.removeFriend(getFUserId(), user.id);
    }

    public static void addFriend(User user) {
        DatabaseService.addFriend(getFUserId(), new Friend(user));
    }

    public static void setAva(int current) {
        DatabaseService.setAva(getFUserId(), current);
    }

    public static void setName(String text) {
        DatabaseService.setName(getFUserId(), text);
    }

    public static void newAchive(Achiev a) {
        DatabaseService.newAciev(getFUserId(), a);
    }

    public static void setBest(int score) {
        DatabaseService.setBest(getFUserId(), score);
    }
//    public boolean ableToBuy(String id){
//        DatabaseService
//    }
}
