package com.example.piratesgames.conf;

import java.util.List;
import java.util.Map;

public class SimulationConfig {

    long seed;

    int K, M, N, C, qStep, T, delT, delSmallT, singleGameCost, initialCredit;

    double cName, cFrom, cTo, cStep;

    double hName, hFrom, hTo, hStep;
    double pName, pFrom, pTo, pStep;
    double prName, prFrom, prTo, prStep;
    double vName, vFrom, vTo, vStep;

    String hStart;
    int hContinuation;

    int pStart, pContinuation;
    int prStart, prContinuation;
    int vStart, vContinuation;

    double[] p;
    double[] del;
    double[] alpha;
    int[] t;

    double alpEma, beta, gamma, eta;
    int delTAggr;

    double fairFloor, ffFrom, ffTo, ffStep;
    double probBossExec, pbeFrom, pbeTo, pbeStep;

    List<String> hAlgos;
    List<String> pAlgos;
    List<String> prAlgos;
    List<String> vAlgos;

    Integer v5KFrom, v5KTo, v6KFrom, v6KTo;

    Map<String, Double> selectedMetrics;
    Map<String, Boolean> debugFlags;


    public SimulationConfig() {
    }

    public SimulationConfig(long seed, int k, int m, int n, int c, int qStep, int t, int delT, int delSmallT, int singleGameCost, int initialCredit, double cName, double cFrom, double cTo, double cStep, double hName, double hFrom, double hTo, double hStep, double pName, double pFrom, double pTo, double pStep, double prName, double prFrom, double prTo, double prStep, double vName, double vFrom, double vTo, double vStep, String hStart, int hContinuation, int pStart, int pContinuation, int prStart, int prContinuation, int vStart, int vContinuation, double[] p, double[] del, double[] alpha, int[] t1, double alpEma, double beta, double gamma, double eta, int delTAggr, double fairFloor, double ffFrom, double ffTo, double ffStep, double probBossExec, double pbeFrom, double pbeTo, double pbeStep, List<String> hAlgos, List<String> pAlgos, List<String> prAlgos, List<String> vAlgos, Integer v5KFrom, Integer v5KTo, Integer v6KFrom, Integer v6KTo, Map<String, Double> selectedMetrics, Map<String, Boolean> debugFlags) {
        this.seed = seed;
        K = k;
        M = m;
        N = n;
        C = c;
        this.qStep = qStep;
        T = t;
        this.delT = delT;
        this.delSmallT = delSmallT;
        this.singleGameCost = singleGameCost;
        this.initialCredit = initialCredit;
        this.cName = cName;
        this.cFrom = cFrom;
        this.cTo = cTo;
        this.cStep = cStep;
        this.hName = hName;
        this.hFrom = hFrom;
        this.hTo = hTo;
        this.hStep = hStep;
        this.pName = pName;
        this.pFrom = pFrom;
        this.pTo = pTo;
        this.pStep = pStep;
        this.prName = prName;
        this.prFrom = prFrom;
        this.prTo = prTo;
        this.prStep = prStep;
        this.vName = vName;
        this.vFrom = vFrom;
        this.vTo = vTo;
        this.vStep = vStep;
        this.hStart = hStart;
        this.hContinuation = hContinuation;
        this.pStart = pStart;
        this.pContinuation = pContinuation;
        this.prStart = prStart;
        this.prContinuation = prContinuation;
        this.vStart = vStart;
        this.vContinuation = vContinuation;
        this.p = p;
        this.del = del;
        this.alpha = alpha;
        this.t = t1;
        this.alpEma = alpEma;
        this.beta = beta;
        this.gamma = gamma;
        this.eta = eta;
        this.delTAggr = delTAggr;
        this.fairFloor = fairFloor;
        this.ffFrom = ffFrom;
        this.ffTo = ffTo;
        this.ffStep = ffStep;
        this.probBossExec = probBossExec;
        this.pbeFrom = pbeFrom;
        this.pbeTo = pbeTo;
        this.pbeStep = pbeStep;
        this.hAlgos = hAlgos;
        this.pAlgos = pAlgos;
        this.prAlgos = prAlgos;
        this.vAlgos = vAlgos;
        this.v5KFrom = v5KFrom;
        this.v5KTo = v5KTo;
        this.v6KFrom = v6KFrom;
        this.v6KTo = v6KTo;
        this.selectedMetrics = selectedMetrics;
        this.debugFlags = debugFlags;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getK() {
        return K;
    }

    public void setK(int k) {
        K = k;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public int getqStep() {
        return qStep;
    }

    public void setqStep(int qStep) {
        this.qStep = qStep;
    }

    public int getT() {
        return T;
    }

    public void setT(int[] t) {
        this.t = t;
    }

    public double getAlpEma() {
        return alpEma;
    }

    public void setAlpEma(double alpEma) {
        this.alpEma = alpEma;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getEta() {
        return eta;
    }

    public void setEta(double eta) {
        this.eta = eta;
    }

    public int getDelTAggr() {
        return delTAggr;
    }

    public void setDelTAggr(int delTAggr) {
        this.delTAggr = delTAggr;
    }

    public double getFairFloor() {
        return fairFloor;
    }

    public void setFairFloor(double fairFloor) {
        this.fairFloor = fairFloor;
    }

    public double getFfFrom() {
        return ffFrom;
    }

    public void setFfFrom(double ffFrom) {
        this.ffFrom = ffFrom;
    }

    public double getFfTo() {
        return ffTo;
    }

    public void setFfTo(double ffTo) {
        this.ffTo = ffTo;
    }

    public double getFfStep() {
        return ffStep;
    }

    public void setFfStep(double ffStep) {
        this.ffStep = ffStep;
    }

    public double getProbBossExec() {
        return probBossExec;
    }

    public void setProbBossExec(double probBossExec) {
        this.probBossExec = probBossExec;
    }

    public double getPbeFrom() {
        return pbeFrom;
    }

    public void setPbeFrom(double pbeFrom) {
        this.pbeFrom = pbeFrom;
    }

    public double getPbeTo() {
        return pbeTo;
    }

    public void setPbeTo(double pbeTo) {
        this.pbeTo = pbeTo;
    }

    public double getPbeStep() {
        return pbeStep;
    }

    public void setPbeStep(double pbeStep) {
        this.pbeStep = pbeStep;
    }

    public List<String> gethAlgos() {
        return hAlgos;
    }

    public void sethAlgos(List<String> hAlgos) {
        this.hAlgos = hAlgos;
    }

    public List<String> getpAlgos() {
        return pAlgos;
    }

    public void setpAlgos(List<String> pAlgos) {
        this.pAlgos = pAlgos;
    }

    public List<String> getPrAlgos() {
        return prAlgos;
    }

    public void setPrAlgos(List<String> prAlgos) {
        this.prAlgos = prAlgos;
    }

    public List<String> getvAlgos() {
        return vAlgos;
    }

    public void setvAlgos(List<String> vAlgos) {
        this.vAlgos = vAlgos;
    }

    public Integer getV5KFrom() {
        return v5KFrom;
    }

    public void setV5KFrom(Integer v5KFrom) {
        this.v5KFrom = v5KFrom;
    }

    public Integer getV5KTo() {
        return v5KTo;
    }

    public void setV5KTo(Integer v5KTo) {
        this.v5KTo = v5KTo;
    }

    public Integer getV6KFrom() {
        return v6KFrom;
    }

    public void setV6KFrom(Integer v6KFrom) {
        this.v6KFrom = v6KFrom;
    }

    public Integer getV6KTo() {
        return v6KTo;
    }

    public void setV6KTo(Integer v6KTo) {
        this.v6KTo = v6KTo;
    }

    public Map<String, Double> getSelectedMetrics() {
        return selectedMetrics;
    }

    public void setSelectedMetrics(Map<String, Double> selectedMetrics) {
        this.selectedMetrics = selectedMetrics;
    }

    public Map<String, Boolean> getDebugFlags() {
        return debugFlags;
    }

    public void setDebugFlags(Map<String, Boolean> debugFlags) {
        this.debugFlags = debugFlags;
    }

    public void setT(int t) {
        T = t;
    }

    public int getDelT() {
        return delT;
    }

    public void setDelT(int delT) {
        this.delT = delT;
    }

    public int getDelSmallT() {
        return delSmallT;
    }

    public void setDelSmallT(int delSmallT) {
        this.delSmallT = delSmallT;
    }

    public int getSingleGameCost() {
        return singleGameCost;
    }

    public void setSingleGameCost(int singleGameCost) {
        this.singleGameCost = singleGameCost;
    }

    public int getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(int initialCredit) {
        this.initialCredit = initialCredit;
    }

    public double getcName() {
        return cName;
    }

    public void setcName(double cName) {
        this.cName = cName;
    }

    public double getcFrom() {
        return cFrom;
    }

    public void setcFrom(double cFrom) {
        this.cFrom = cFrom;
    }

    public double getcTo() {
        return cTo;
    }

    public void setcTo(double cTo) {
        this.cTo = cTo;
    }

    public double getcStep() {
        return cStep;
    }

    public void setcStep(double cStep) {
        this.cStep = cStep;
    }

    public double gethName() {
        return hName;
    }

    public void sethName(double hName) {
        this.hName = hName;
    }

    public double gethFrom() {
        return hFrom;
    }

    public void sethFrom(double hFrom) {
        this.hFrom = hFrom;
    }

    public double gethTo() {
        return hTo;
    }

    public void sethTo(double hTo) {
        this.hTo = hTo;
    }

    public double gethStep() {
        return hStep;
    }

    public void sethStep(double hStep) {
        this.hStep = hStep;
    }

    public double getpName() {
        return pName;
    }

    public void setpName(double pName) {
        this.pName = pName;
    }

    public double getpFrom() {
        return pFrom;
    }

    public void setpFrom(double pFrom) {
        this.pFrom = pFrom;
    }

    public double getpTo() {
        return pTo;
    }

    public void setpTo(double pTo) {
        this.pTo = pTo;
    }

    public double getpStep() {
        return pStep;
    }

    public void setpStep(double pStep) {
        this.pStep = pStep;
    }

    public double getPrName() {
        return prName;
    }

    public void setPrName(double prName) {
        this.prName = prName;
    }

    public double getPrFrom() {
        return prFrom;
    }

    public void setPrFrom(double prFrom) {
        this.prFrom = prFrom;
    }

    public double getPrTo() {
        return prTo;
    }

    public void setPrTo(double prTo) {
        this.prTo = prTo;
    }

    public double getPrStep() {
        return prStep;
    }

    public void setPrStep(double prStep) {
        this.prStep = prStep;
    }

    public double getvName() {
        return vName;
    }

    public void setvName(double vName) {
        this.vName = vName;
    }

    public double getvFrom() {
        return vFrom;
    }

    public void setvFrom(double vFrom) {
        this.vFrom = vFrom;
    }

    public double getvTo() {
        return vTo;
    }

    public void setvTo(double vTo) {
        this.vTo = vTo;
    }

    public double getvStep() {
        return vStep;
    }

    public void setvStep(double vStep) {
        this.vStep = vStep;
    }

    public String gethStart() {
        return hStart;
    }

    public void sethStart(String hStart) {
        this.hStart = hStart;
    }

    public int gethContinuation() {
        return hContinuation;
    }

    public void sethContinuation(int hContinuation) {
        this.hContinuation = hContinuation;
    }

    public int getpStart() {
        return pStart;
    }

    public void setpStart(int pStart) {
        this.pStart = pStart;
    }

    public int getpContinuation() {
        return pContinuation;
    }

    public void setpContinuation(int pContinuation) {
        this.pContinuation = pContinuation;
    }

    public int getPrStart() {
        return prStart;
    }

    public void setPrStart(int prStart) {
        this.prStart = prStart;
    }

    public int getPrContinuation() {
        return prContinuation;
    }

    public void setPrContinuation(int prContinuation) {
        this.prContinuation = prContinuation;
    }

    public int getvStart() {
        return vStart;
    }

    public void setvStart(int vStart) {
        this.vStart = vStart;
    }

    public int getvContinuation() {
        return vContinuation;
    }

    public void setvContinuation(int vContinuation) {
        this.vContinuation = vContinuation;
    }

    public double[] getP() {
        return p;
    }

    public void setP(double[] p) {
        this.p = p;
    }

    public double[] getDel() {
        return del;
    }

    public void setDel(double[] del) {
        this.del = del;
    }

    public double[] getAlpha() {
        return alpha;
    }

    public void setAlpha(double[] alpha) {
        this.alpha = alpha;
    }

}

