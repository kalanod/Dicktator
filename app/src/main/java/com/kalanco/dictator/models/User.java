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
        img = 1;
        achivments.add(new Achiev("Новичок", "Открыть игру в 1й раз", "1" , true));

    }

}
