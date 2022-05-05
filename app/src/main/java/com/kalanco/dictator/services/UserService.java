package com.kalanco.dictator.services;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.kalanco.dictator.models.User;

public class UserService {
    public static Task<AuthResult> storeUser(String name, String password, String email) {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(authResult.getUser().getUid(), name, email);
                        DatabaseService.storeUser(user);
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

    public static void refreshData() {

        getDatabaseUser().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                User user;
                user = dataSnapshot.getValue(User.class);
                user.refresh();
                DatabaseService.storeUser(user);
            }
        });
    }

    public static Task<DataSnapshot> getDatabaseUser() {
        return DatabaseService.getDatabaseUser(getFUserId());
    }
    public static void getDatabaseUser(String id) {
        DatabaseService.getDatabaseUser(id);
    }
//    public boolean ableToBuy(String id){
//        DatabaseService
//    }
}
