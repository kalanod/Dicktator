package com.kalanco.dictator.models;

import java.util.HashMap;

public class User {
    public String name;
    public String id;
    public String email;
    public int score;
    public int money;
    //public List<ShopItem> builds = new LinkedList<>();
    public HashMap<String, ShopItem> builds;

    public User() {
    }

    public User(String id, String name, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.score = 0;
        this.money = 0;
        builds = new HashMap<>();
        this.builds.put("0", new ShopItem("0", "Тюрьма", "Позволяет устранять неугодных конкурентов", 10000));
        this.builds.put("1", new ShopItem("1", "нефтяная вышка", "даёт пассивный доход каждый ход", 10000));
    }

    public User(String name, String id, String email, int score, int money, HashMap<String, ShopItem> builds) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.score = 0;
        this.money = 0;
        builds = new HashMap<>();
        this.builds.put("0", new ShopItem("0", "Тюрьма", "Позволяет устранять неугодных конкурентов", 10000));
        this.builds.put("1", new ShopItem("1", "нефтяная вышка", "даёт пассивный доход каждый ход", 10000));

    }

    public User(String id) {
        this.name = "name";
        this.id = id;
        this.email = "email";
        this.score = 0;
        this.money = 0;
        builds = new HashMap<>();
        this.builds.put("0", new ShopItem("0", "Тюрьма", "Позволяет устранять неугодных конкурентов", 10000));
        this.builds.put("1", new ShopItem("1", "нефтяная вышка", "даёт пассивный доход каждый ход", 10000));
    }
    public void refresh(){
        this.score = 0;
        this.money = 0;
        builds.clear();
        this.builds.put("0", new ShopItem("0", "Тюрьма", "Позволяет устранять неугодных конкурентов", 10000));
        this.builds.put("1", new ShopItem("1", "нефтяная вышка", "даёт пассивный доход каждый ход", 10000));
    }

}
