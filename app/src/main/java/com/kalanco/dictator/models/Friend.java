package com.kalanco.dictator.models;

public class Friend {
    public String id;
    public String name;
    public int img;

    public Friend() {
    }
    public Friend(User user) {
        id = user.id;
        name = user.name;
        img = user.img;
    }

    public Friend(String id, String name, int img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }
}
