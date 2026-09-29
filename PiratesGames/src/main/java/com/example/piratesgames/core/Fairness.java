package com.example.piratesgames.core;

import java.util.Arrays;

public class Fairness {

    public Fairness() {}

    public double envyFreeness(int[] payoffs) {
        if (payoffs.length == 0) return 0.0;

        double sum = 0.0;
        for (int p : payoffs) sum += Math.max(0, p);
        if (sum == 0.0) return 0.0;

        double maxEnvy = 0.0;

        for (int i = 0; i < payoffs.length; i++) {
            double ui = Math.max(0, payoffs[i]) / sum;
            for (int j = 0; j < payoffs.length; j++) {
                if (i == j) continue;
                double uj = Math.max(0, payoffs[j]) / sum;
                maxEnvy = Math.max(maxEnvy, uj - ui);
            }
        }
        return clamp01(1.0 - maxEnvy);
    }

    public double egalitarian(int[] payoffs) {
        if (payoffs.length == 0) return 0.0;

        double sum = 0.0;
        for (int p : payoffs) sum += Math.max(0, p);
        if (sum == 0.0) return 0.0;

        double min = Double.POSITIVE_INFINITY;
        for (int p : payoffs) {
            double ui = Math.max(0, p) / sum;
            min = Math.min(min, ui);
        }
        return clamp01(min);
    }

    public double proportionality(int[] payoffs) {
        int n = payoffs.length;
        if (n == 0) return 0.0;

        double sum = 0.0;
        for (int p : payoffs) sum += Math.max(0, p);
        if (sum == 0.0) return 0.0;

        double minRatio = Double.POSITIVE_INFINITY;
        for (int p : payoffs) {
            double ui = Math.max(0, p) / sum;
            minRatio = Math.min(minRatio, ui * n);
        }
        return clamp01(Math.min(1.0, minRatio));
    }

    public double individualRationality(int[] payoffs) {
        for (int p : payoffs) {
            if (p < 0) return 0.0;
        }
        return 1.0;
    }

    public double totalFairness(
            int[] payoffs,
            double wEnvy,
            double wEgal,
            double wProp,
            double wIR
    ) {
        double fEnvy = envyFreeness(payoffs);
        double fEgal = egalitarian(payoffs);
        double fProp = proportionality(payoffs);
        double fIR   = individualRationality(payoffs);

        double sumW = wEnvy + wEgal + wProp + wIR;
        if (sumW == 0.0) return 0.0;

        return clamp01(
                (wEnvy * fEnvy + wEgal * fEgal + wProp * fProp + wIR * fIR) / sumW
        );
    }

    private double clamp01(double x) {
        return Math.max(0.0, Math.min(1.0, x));
    }


}
