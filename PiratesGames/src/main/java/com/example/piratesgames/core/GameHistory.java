package com.example.piratesgames.core;

import java.util.ArrayList;
import java.util.List;

public class GameHistory {
    List<SingleGame> singleGames = new ArrayList<>();

    public GameHistory() {
    }

    public GameHistory(List<SingleGame> singleGames) {
        this.singleGames = singleGames;
    }

    public List<SingleGame> getSingleGames() {
        return singleGames;
    }

    public void setSingleGames(List<SingleGame> singleGames) {
        this.singleGames = singleGames;
    }
}
