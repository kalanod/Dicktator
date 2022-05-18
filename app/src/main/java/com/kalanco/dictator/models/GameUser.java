package com.kalanco.dictator.models;

public class GameUser {
    public int money;
    public int score;
    public int best;
    public int loyal;
    public int police;

    public GameUser(int money, int score, int best, int loyal, int police) {
        this.money = money;
        this.score = score;
        this.best = best;
        this.loyal = loyal;
        this.police = police;
    }

    public GameUser() {
    }
}
