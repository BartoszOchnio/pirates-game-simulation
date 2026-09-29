package com.example.piratesgames.core;

public class BoatDNA {
    private String hCurr;
    private int del_t;
    private String hNext;
    private double hMut;
    private String pCurr;
    private String pNext;
    private double pMut;
    private String prCurr;
    private String prNext;
    private double prMut;
    private String vCurr;
    private String vNext;
    private double vMut;
    private int det_T;



    public BoatDNA(String hCurr, int del_t, String hNext, double hMut, String pCurr,
                   String pNext, double pMut, String prCurr, String prNext, double prMut,
                   String vCurr, String vNext, double vMut, int det_T) {
        this.hCurr = hCurr;
        this.del_t = del_t;
        this.hNext = hNext;
        this.hMut = hMut;
        this.pCurr = pCurr;
        this.pNext = pNext;
        this.pMut = pMut;
        this.prCurr = prCurr;
        this.prNext = prNext;
        this.prMut = prMut;
        this.vCurr = vCurr;
        this.vNext = vNext;
        this.vMut = vMut;
        this.det_T = det_T;
    }

    public BoatDNA(BoatDNA other) {
        this.hCurr = other.hCurr;
        this.del_t = other.del_t;
        this.hNext = other.hNext;
        this.hMut = other.hMut;
        this.pCurr = other.pCurr;
        this.pNext = other.pNext;
        this.pMut = other.pMut;
        this.prCurr = other.prCurr;
        this.prNext = other.prNext;
        this.prMut = other.prMut;
        this.vCurr = other.vCurr;
        this.vNext = other.vNext;
        this.vMut = other.vMut;
        this.det_T = other.det_T;
    }

    @Override
    public String toString() {
        return "Boat DNA: " +
                "| " + hCurr + '\'' +
                ", " + del_t +
                ", " + hNext + '\'' +
                ", " + hMut +
                "| " + pCurr + '\'' +
                ", " + det_T +
                ", " + pNext + '\'' +
                ", " + pMut +
                "| " + prCurr + '\'' +
                ", " + det_T +
                ", " + prNext + '\'' +
                ", " + prMut +
                "| " + vCurr + '\'' +
                ", " + det_T +
                ", " + vNext + '\'' +
                ", " + vMut +
                "|";
    }

    public String gethCurr() {
        return hCurr;
    }

    public void sethCurr(String hCurr) {
        this.hCurr = hCurr;
    }

    public int getDel_t() {
        return del_t;
    }

    public void setDel_t(int del_t) {
        this.del_t = del_t;
    }

    public String gethNext() {
        return hNext;
    }

    public void sethNext(String hNext) {
        this.hNext = hNext;
    }

    public double gethMut() {
        return hMut;
    }

    public void sethMut(double hMut) {
        this.hMut = hMut;
    }

    public String getpCurr() {
        return pCurr;
    }

    public void setpCurr(String pCurr) {
        this.pCurr = pCurr;
    }

    public String getpNext() {
        return pNext;
    }

    public void setpNext(String pNext) {
        this.pNext = pNext;
    }

    public double getpMut() {
        return pMut;
    }

    public void setpMut(double pMut) {
        this.pMut = pMut;
    }

    public String getPrCurr() {
        return prCurr;
    }

    public void setPrCurr(String prCurr) {
        this.prCurr = prCurr;
    }

    public String getPrNext() {
        return prNext;
    }

    public void setPrNext(String prNext) {
        this.prNext = prNext;
    }

    public double getPrMut() {
        return prMut;
    }

    public void setPrMut(double prMut) {
        this.prMut = prMut;
    }

    public String getvCurr() {
        return vCurr;
    }

    public void setvCurr(String vCurr) {
        this.vCurr = vCurr;
    }

    public String getvNext() {
        return vNext;
    }

    public void setvNext(String vNext) {
        this.vNext = vNext;
    }

    public double getvMut() {
        return vMut;
    }

    public void setvMut(double vMut) {
        this.vMut = vMut;
    }

    public int getDet_T() {
        return det_T;
    }

    public void setDet_T(int det_T) {
        this.det_T = det_T;
    }


}
