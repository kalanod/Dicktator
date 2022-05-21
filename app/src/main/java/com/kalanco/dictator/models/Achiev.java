package com.kalanco.dictator.models;

public class Achiev {
    public String title;
    public String desc;
    public String imgSrc;
    public boolean isGet;

    public Achiev() {
    }

    public Achiev(String title, String desc, String imgSrc, boolean isGet) {
        this.title = title;
        this.desc = desc;
        this.imgSrc = imgSrc;
        this.isGet = isGet;
    }

    @Override
    public String toString() {
        return "Achiev{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
