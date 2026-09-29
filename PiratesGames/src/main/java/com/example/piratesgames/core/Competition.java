package com.example.piratesgames.core;

public class Competition {
    private int n;
    private int m;
    private double [][] oldMatrix;
    private double [][] nb;
    private double [][] newMatrix;
    private String path = "../../";
    private String fileName;
    public Competition() {
    }
    public Competition(Competition other){
        this.n = other.getN();
        this.m = other.getM();
        this.oldMatrix = other.getNewMatrix();
    }

    public Competition(double[][] matrix) {
        this.oldMatrix = matrix;
        this.m = matrix.length;
        this.n = matrix[0].length;
    }


    public void playSingleCompetition() {
        int m = oldMatrix.length;
        int n = oldMatrix[0].length;

        this.nb = buildNeighborMatrixToroidal(this.oldMatrix);

        System.out.println("\n");

        this.newMatrix = new double[m][n];


        System.out.println("fields m,n = " + this.m + "," + this.n);
        System.out.println("matrix m,n = " + this.oldMatrix.length + "," + this.oldMatrix[0].length);
        System.out.println("nb m,n = " + (this.oldMatrix.length+2) + "," + (this.oldMatrix[0].length+2));


        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                int I = i + 1;
                int J = j + 1;

                double max = Double.NEGATIVE_INFINITY;

                max = nb[I][J];
                max = Math.max(max, nb[I-1][J]);   // N
                max = Math.max(max, nb[I-1][J+1]); // NE
                max = Math.max(max, nb[I][J+1]);   // E
                max = Math.max(max, nb[I+1][J+1]); // SE
                max = Math.max(max, nb[I+1][J]);   // S
                max = Math.max(max, nb[I+1][J-1]); // SW
                max = Math.max(max, nb[I][J-1]);   // W
                max = Math.max(max, nb[I-1][J-1]); // NW

                newMatrix[i][j] = max;
            }
        }
    }

    public static double[][] buildNeighborMatrixToroidal(double[][] a) {
        int m = a.length;
        int n = a[0].length;

        double[][] nb = new double[m + 2][n + 2];

        for (int i = 0; i < m; i++) {
            System.arraycopy(a[i], 0, nb[i + 1], 1, n);
        }

        for (int j = 0; j < n; j++) {
            nb[0][j + 1] = a[m - 1][j];
            nb[m + 1][j + 1] = a[0][j];
        }

        // 3) lewa i prawa "ghost column"
        for (int i = 0; i < m; i++) {
            nb[i + 1][0] = a[i][n - 1];
            nb[i + 1][n + 1] = a[i][0];
        }

        // 4) narożniki
        nb[0][0] = a[m - 1][n - 1];
        nb[0][n + 1] = a[m - 1][0];
        nb[m + 1][0] = a[0][n - 1];
        nb[m + 1][n + 1] = a[0][0];

        return nb;
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


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double[][] getOldMatrix() {
        return oldMatrix;
    }

    public void setOldMatrix(double[][] oldMatrix) {
        this.oldMatrix = oldMatrix;
    }

    public double[][] getNb() {
        return nb;
    }

    public void setNb(double[][] nb) {
        this.nb = nb;
    }

    public double[][] getNewMatrix() {
        return newMatrix;
    }

    public void setNewMatrix(double[][] newMatrix) {
        this.newMatrix = newMatrix;
    }
}
