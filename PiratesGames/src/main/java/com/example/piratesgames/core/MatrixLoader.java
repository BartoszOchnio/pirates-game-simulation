package com.example.piratesgames.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MatrixLoader {

    public static final String DATA_DIR = "../../Data/";

    public static int[][] loadMatrix(String fileName, int m, int n) throws IOException {
        if (m <= 0 || n <= 0) {
            throw new IllegalArgumentException("Matrix dimensions must be positive: m=" + m + ", n=" + n);
        }

        int[][] matrix = new int[m][n];

        File file = new File(DATA_DIR, fileName);
        if (!file.exists()) {
            throw new IOException("Matrix file not found: " + file.getAbsolutePath());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (row >= m) {
                    throw new IOException("File has more than " + m + " data rows. Extra row: " + line);
                }

                String[] tokens = line.split("\\s+");

                if (tokens.length != n) {
                    throw new IOException("Invalid row length. Expected " + n + " columns, got "
                            + tokens.length + ". Row: " + line);
                }

                for (int col = 0; col < n; col++) {
                    matrix[row][col] = Integer.parseInt(tokens[col]);
                }

                row++;
            }

            if (row != m) {
                throw new IOException("Matrix does not have " + m + " data rows. Got " + row
                        + " in file: " + fileName);
            }
        }

        return matrix;
    }

    public static double[][] readMatrixFromFile(String fileName, int m, int n) throws IOException {
        Path file = Path.of(DATA_DIR, fileName).toAbsolutePath().normalize();
        return readMatrixFromFile(file, m, n);
    }


    public static double[][] readMatrixFromFile(Path file, int m, int n) throws IOException {

        double[][] matrix = new double[m][n];
        int count = 0;

        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null && count < m * n) {
                line = line.trim();
                if (line.isEmpty()) continue;

                double value = Double.parseDouble(line);

                int row = count / n;
                int col = count % n;
                matrix[row][col] = value;

                count++;
            }
        }

        if (count < n * m) {
            throw new IOException("File contains only " + count +
                    " numbers, but " + (n * m) + " required.");
        }

        return matrix;
    }
}

