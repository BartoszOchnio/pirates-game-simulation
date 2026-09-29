package com.example.piratesgames.core;

public class Player {
    private int id;
    private int coins;

    public Player() {
    }

    public Player(int id, int coins) {
        this.id = id;
        this.coins = coins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
