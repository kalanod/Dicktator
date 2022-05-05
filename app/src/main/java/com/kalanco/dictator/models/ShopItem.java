package com.kalanco.dictator.models;

public class ShopItem {
    public String name;
    public String desc;
    public int price;
    public boolean isBought;
    public String id;

    public ShopItem() {
    }

    public ShopItem(String id, String name, String desc, int price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.isBought = false;
    }
}
