package com.example.piratesgames.core;

public class Matrix {
    private int m;
    private int n;
    private int [][] data;


    public void printMatrix() {
        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.printf("%4d", data[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix(int m, int n, int[][] matrix) {
        this.m = m;
        this.n = n;
        this.data = matrix;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int[][] getData() {
        return data;
    }

    public void setD(int[][] matix) {
        this.data = matix;
    }
}
