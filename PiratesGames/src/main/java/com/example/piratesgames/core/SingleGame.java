package com.example.piratesgames.core;

import com.example.piratesgames.strategies.v.V5;
import com.example.piratesgames.strategies.v.V6;

import java.util.*;

public class SingleGame {

    private BoatDNA boatDNA;
    private int [] players;
    private boolean[] decisions = null;
    private int [] payments = null;
    private int [] vK;
    Map<String, Double> metrics = new HashMap<>();
    double totalFairenss = 0;
    private int lastRound = 0;



    public SingleGame() {
    }

    public SingleGame(int[] players , BoatDNA boatDNA, boolean [] prevDecisions) {
        this.players = players;
        this.boatDNA = boatDNA;
        this.vK = new int[players.length];
        this.decisions = prevDecisions;
    }

    public void playSingleGamev2(StrategyMap strategyMap, Map<String, Double> selectedMetrics){
        System.out.println("\n" + this.boatDNA.toString());

        int prevNo = 0;
        for(int i=0; i<9; i++){
            if(!decisions[i])
                prevNo ++;
        }
        int piratesCount = 9;
        boolean isAgreement = false;
        int[] p;
        for(int i=0; i<9; i++){
            p = strategyMap.getP_pr_v_map().get(this.boatDNA.getpCurr())[i];
            decisions[i] = true;
            int currYes = 1;
            for(int j=i+1; j<9; j++){
                switch (boatDNA.getvCurr()) {
                    case "V5" -> {
                        V5 v5 = new V5();
                        isAgreement = v5.voteV5(prevNo, this.vK[j]);
                    }
                    case "V6" -> {
                        V6 v6 = new V6();
                        isAgreement = v6.voteV6(prevNo, this.vK[j]);
                    }
                }
                decisions[j] = isAgreement;
                if(isAgreement)
                    currYes++;
            }
            if(currYes >= piratesCount - currYes){
                this.payments = p;
                this.lastRound = i+1;
                calculateMetrics(selectedMetrics, payments);
                i=9;
            }else{
                for(int j=0; j<9; j++)
                    decisions[j] = false;
                piratesCount--;
            }

        }

    }

    public void calculateMetrics(Map<String, Double> selectedMetrics, int[] payoff){
        this.totalFairenss = 0.0;
        this.metrics = new HashMap<>();
        Fairness fairness = new Fairness();
        for (Map.Entry<String, Double> entry : selectedMetrics.entrySet()) {

            double f = 0.0;

            switch (entry.getKey()) {
                case "envy_freeness" -> {
                    f = fairness.envyFreeness(payoff);
                }
                case "egalitarian" -> {
                    f = fairness.egalitarian(payoff);
                }
                case "proportionality" -> {
                    f = fairness.proportionality(payoff);
                }
                case "individual_rationality" -> {
                    f = fairness.individualRationality(payoff);
                }
            }
            this.metrics.put(entry.getKey(), f);

            this.totalFairenss = this.totalFairenss + entry.getValue() * f;
        }

    }

    public BoatDNA getBoatDNA() {
        return boatDNA;
    }

    public void setBoatDNA(BoatDNA boatDNA) {
        this.boatDNA = boatDNA;
    }


    public int[] getPlayers() {
        return players;
    }

    public void setPlayers(int[] players) {
        this.players = players;
    }

    public int[] getvK() {
        return vK;
    }

    public void setvK(int[] vK) {
        this.vK = vK;
    }

    public boolean[] getDecisions() {
        return decisions;
    }

    public void setDecisions(boolean[] decisions) {
        this.decisions = decisions;
    }

    public int[] getPayments() {
        return payments;
    }

    public void setPayments(int[] payments) {
        this.payments = payments;
    }

    public Map<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Double> metrics) {
        this.metrics = metrics;
    }

    public double getTotalFairenss() {
        return totalFairenss;
    }

    public void setTotalFairenss(double totalFairenss) {
        this.totalFairenss = totalFairenss;
    }

    public int getLastRound() {
        return lastRound;
    }

    public void setLastRound(int lastRound) {
        this.lastRound = lastRound;
    }


}
