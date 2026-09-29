package com.example.piratesgames.core;

import java.util.ArrayList;
import java.util.List;

public class Boat {
    private int id;
    private int n = 9;
    private int players [] = new int[9];
    private int c;
    List<SingleGame> gamesHistory = new ArrayList<>();

    public Boat(int id, int n, int[] players, int c) {
        this.id = id;
        this.n = n;
        this.players = players;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Boat " + (id + 1);
    }




    public Boat(int c){
        this.c = c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int[] getPlayers() {
        return players;
    }

    public void setPlayer(int[] player) {
        this.players = player;
    }


    public void setPlayers(int[] players) {
        this.players = players;
    }

    public List<SingleGame> getGamesHistory() {
        return gamesHistory;
    }

    public void setGamesHistory(List<SingleGame> gamesHistory) {
        this.gamesHistory = gamesHistory;
    }
}
