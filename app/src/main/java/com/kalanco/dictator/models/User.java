package com.kalanco.dictator.models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    public String name;
    public String id;
    public String email;
    public int best;
    public int img;
    public ArrayList<Achiev> achivments = new ArrayList<>();
    public ArrayList<Friend> friends = new ArrayList<>();

    public User() {
    }

    public User(String id, String name, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
        best = 0;
        img = 0;
        achivments.add(new Achiev("kkall", "kalabal", "qwd" , true));
        achivments.add(new Achiev("kkall2", "kalabal2", "qwd" , true));
        achivments.add(new Achiev("kkall3", "kalabal 3 false", "qwd" , false));

        friends.add(new Friend("ahah", "abob", 4));
        friends.add(new Friend("ahwwwah", "abob", 4));
        friends.add(new Friend("kalll", "abob", 4));

    }

}
