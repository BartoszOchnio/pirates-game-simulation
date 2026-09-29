package com.example.piratesgames.strategies.v;



public class V5 {
    public boolean voteV5(int noCountPrevGame, int k) {
        return noCountPrevGame <= k; // true = YES, false = NO
    }
}